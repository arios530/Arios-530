import com.jogamp.opengl.GL2;

import java.nio.FloatBuffer;
import com.jogamp.opengl.GL2;
//import javax.media.opengl.GL;
final class Class127_Sub1 extends Class127 {

   private static int anInt2682;


   Class127_Sub1() {
      new Class17();
      new Class61();
   }

   static final void method1755() {
      GL2 var0 = Class138.aGL1804;
      if(var0.isExtensionAvailable("GL_ARB_point_parameters")) {
         float[] var1 = new float[]{1.0F, 0.0F, 5.0E-4F};
         var0.glPointParameterfv('\u8129', var1, 0);
         FloatBuffer var2 = FloatBuffer.allocate(1);
         var0.glGetFloatv('\u8127', var2);
         float var3 = var2.get(0);
         if(var3 > 1024.0F) {
            var3 = 1024.0F;
         }

         var0.glPointParameterf('\u8126', 1.0F);
         var0.glPointParameterf('\u8127', var3);
      }

      if(var0.isExtensionAvailable("GL_ARB_point_sprite")) {
         ;
      }

   }

   static final void method1756() {}

   static final int method1757() {
      return anInt2682;
   }

   static final void method1758(int var0) {
      anInt2682 = var0;
   }

   final void method1759() {}

   static {
      new Class128(8);
      anInt2682 = 2;
      new RSByteBuffer(131056);
   }
}
