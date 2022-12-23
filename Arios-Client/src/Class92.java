import com.jogamp.opengl.GL2;
//import javax.media.opengl.GL;
final class Class92 {

   static float[] aFloatArray1312 = new float[4];
   private static int anInt1313 = -1;
   static int anInt1314;
   static int anInt1315;
   static int anInt1316 = 13156520;
   private static float aFloat1317 = -1.0F;
   private static float aFloat1318 = -1.0F;
   static float[] aFloatArray1319 = new float[4];
   private static float aFloat1320;
   private static float[] aFloatArray1321 = new float[4];
   static int anInt1322 = 16777215;
   private static int anInt1323 = -1;
   private static int anInt1324 = -1;


   static final void method1504() {
      GL2 var0 = Class138.aGL1804;
      var0.glLightfv(16384, 4611, aFloatArray1312, 0);
      var0.glLightfv(16385, 4611, aFloatArray1321, 0);
   }

   static final float method1505() {
      return aFloat1317;
   }

   static final void method1506(int var0, float var1, float var2, float var3) {
      if(anInt1313 != var0 || aFloat1320 != var1 || aFloat1317 != var2 || aFloat1318 != var3) {
         anInt1313 = var0;
         aFloat1320 = var1;
         aFloat1317 = var2;
         aFloat1318 = var3;
         GL2 var4 = Class138.aGL1804;
         float var5 = (float)(var0 >> 16 & 255) / 255.0F;
         float var6 = (float)(var0 >> 8 & 255) / 255.0F;
         float var7 = (float)(var0 & 255) / 255.0F;
         float[] var8 = new float[]{var1 * var5, var1 * var6, var1 * var7, 1.0F};
         var4.glLightModelfv(2899, var8, 0);
         float[] var9 = new float[]{var2 * var5, var2 * var6, var2 * var7, 1.0F};
         var4.glLightfv(16384, 4609, var9, 0);
         float[] var10 = new float[]{-var3 * var5, -var3 * var6, -var3 * var7, 1.0F};
         var4.glLightfv(16385, 4609, var10, 0);
      }
   }

   public static void method1507() {
      aFloatArray1312 = null;
      aFloatArray1321 = null;
      aFloatArray1319 = null;
   }

   static void method1508(int var0, int var1) {
      if(anInt1324 != var0 || anInt1323 != var1) {
         anInt1324 = var0;
         anInt1323 = var1;
         GL2 var2 = Class138.aGL1804;
         //short var4 = 3584;
         int fogEnd = Class40.render_distance * 128; //fog end
         aFloatArray1319[0] = (float)(var0 >> 16 & 255) / 255.0F;
         aFloatArray1319[1] = (float)(var0 >> 8 & 255) / 255.0F;
         aFloatArray1319[2] = (float)(var0 & 255) / 255.0F;
         var2.glFogi(GL2.GL_FOG_MODE, GL2.GL_LINEAR);
         var2.glFogf(GL2.GL_FOG_DENSITY, 0.95F);
         var2.glHint(GL2.GL_FOG_HINT, GL2.GL_FASTEST);
         int fogStart = fogEnd - (int) ((((float) Class40.render_distance / 28.0f) * 256.0f) * 2.0f) - var1;
         if (fogStart < 50) {
             fogStart = 50;
         }
         var2.glFogf(GL2.GL_FOG_START, (float) fogStart);
         var2.glFogf(GL2.GL_FOG_END, (float) fogEnd - (((float) Class40.render_distance / 28.0f) * 256.0f)); // baseFogStart - 256
         var2.glFogfv(GL2.GL_FOG_COLOR, aFloatArray1319, 0);
      }
   }

   static final void method1509(float var0, float var1, float var2) {
      if(aFloatArray1312[0] != var0 || aFloatArray1312[1] != var1 || aFloatArray1312[2] != var2) {
         aFloatArray1312[0] = var0;
         aFloatArray1312[1] = var1;
         aFloatArray1312[2] = var2;
         aFloatArray1321[0] = -var0;
         aFloatArray1321[1] = -var1;
         aFloatArray1321[2] = -var2;
         anInt1314 = (int)(var0 * 256.0F / var1);
         anInt1315 = (int)(var2 * 256.0F / var1);
      }
   }

   static final int method1510() {
      return anInt1313;
   }

   static final void method1511() {
      GL2 var0 = Class138.aGL1804;
      var0.glColorMaterial(1028, 5634);
      var0.glEnable(2903);
      float[] var1 = new float[]{0.0F, 0.0F, 0.0F, 1.0F};
      var0.glLightfv(16384, 4608, var1, 0);
      var0.glEnable(16384);
      float[] var2 = new float[]{0.0F, 0.0F, 0.0F, 1.0F};
      var0.glLightfv(16385, 4608, var2, 0);
      var0.glEnable(16385);
      anInt1313 = -1;
      anInt1324 = -1;
      method1513();
   }

   static final void method1512(float[] var0) {
      if(var0 == null) {
         var0 = aFloatArray1319;
      }

      GL2 var1 = Class138.aGL1804;
      var1.glFogfv(2918, var0, 0);
   }

   private static final void method1513() {
      method1506(anInt1322, 1.1523438F, 0.69921875F, 1.2F);
      method1509(-50.0F, -60.0F, -50.0F);
      method1508(anInt1316, 0);
   }

   static final float method1514() {
      return aFloat1320;
   }

}
