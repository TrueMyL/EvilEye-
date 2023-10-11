package mjt.lef.asm.transformers.render;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import mjt.lef.client.Agent;
import mjt.lef.client.listener.bus.EventBus;
import mjt.lef.client.listener.events.input.EventAttackReach;
import mjt.lef.client.listener.events.render.EventRender3D;
import mjt.lef.asm.api.annotation.Inject;
import mjt.lef.asm.wrapper.Wrapper;
import mapping.Mappings;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class EntityRendererTransformer extends Wrapper {
    public EntityRendererTransformer() {
        super("net/minecraft/client/renderer/EntityRenderer");
    }

    @Inject(method = "func_175068_a", descriptor = "(IFJ)V")
    public void renderWorldPass(MethodNode methodNode) {
        AbstractInsnNode ldcNode = null;
        for (int i = 0; i < methodNode.instructions.size(); ++i) {
            AbstractInsnNode a = methodNode.instructions.get(i);
            if (a instanceof MethodInsnNode) {
                MethodInsnNode m = (MethodInsnNode) a;
                if (m.owner.equals(Mappings.getUnobfClass("net/minecraft/profiler/Profiler"))
                        && m.name.equals(Mappings.seargeToNotchMethod("func_76318_c"))) { // endStartSection

                    ldcNode = a;
                }
            }
        }

        int partialTicksVar = -1;
        List<LocalVariableNode> locals = methodNode.localVariables;
        for (LocalVariableNode lvn : locals) {
            if (lvn.desc.equals("F")) {
                partialTicksVar = lvn.index;
                break;
            }
        }

        if (ldcNode == null || partialTicksVar == -1) return;

        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Agent.class), "getBus", "()Lmjt/lef/client/listener/bus/EventBus;", false));
        list.add(new TypeInsnNode(NEW, Type.getInternalName(EventRender3D.class)));
        list.add(new InsnNode(DUP));
        list.add(new VarInsnNode(FLOAD, partialTicksVar));
        list.add(new MethodInsnNode(INVOKESPECIAL, Type.getInternalName(EventRender3D.class), "<init>", "(F)V", false));
        list.add(new MethodInsnNode(INVOKEVIRTUAL, Type.getInternalName(EventBus.class), "dispatch", "(Ljava/lang/Object;)Z", false));
        list.add(new InsnNode(POP));

        methodNode.instructions.insert(ldcNode, list);

    }

    @Inject(method = "func_78473_a", descriptor = "(F)V")
    public void getMouseOver(MethodNode methodNode) {

        InsnList list = new InsnList();

        list.add(new TypeInsnNode(NEW, Type.getInternalName(EventAttackReach.class)));
        list.add(new InsnNode(DUP));
        list.add(new LdcInsnNode(3.0));
        list.add(new MethodInsnNode(INVOKESPECIAL, Type.getInternalName(EventAttackReach.class), "<init>", "(D)V", false));
        list.add(new VarInsnNode(ASTORE, 23));

        list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Agent.class), "getBus", "()Lmjt/lef/client/listener/bus/EventBus;", false));
        list.add(new VarInsnNode(ALOAD, 23));
        list.add(new MethodInsnNode(INVOKEVIRTUAL, Type.getInternalName(EventBus.class), "dispatch", "(Ljava/lang/Object;)Z", false));
        list.add(new InsnNode(POP));

        methodNode.instructions.insert(list);

        // above is checked

        LdcInsnNode ldc = null;
        for (int i = 0; i < methodNode.instructions.size(); ++i) {
            AbstractInsnNode x = methodNode.instructions.get(i);

            if (x instanceof LdcInsnNode) {
                LdcInsnNode t = (LdcInsnNode) x;

                if (t.cst instanceof Double && ((Double) t.cst) == 3.0) {
                    ldc = t;
                }
            }
        }

        if (ldc == null) return;

//        methodNode.instructions.insert(ldc, new MethodInsnNode(INVOKEVIRTUAL, Type.getInternalName(EventAttackReach.class), "getReach", "()D", false));
//        methodNode.instructions.insert(ldc, new VarInsnNode(ALOAD, 23));
//        methodNode.instructions.remove(ldc);

    }
}
