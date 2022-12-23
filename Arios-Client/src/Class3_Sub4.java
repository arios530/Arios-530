
final class Class3_Sub4 extends Class3 {

   int anInt2248;
   static int anInt2249;
   int anInt2250;
   static int anInt2251;
   static RSString aClass94_2252 = createRSString("Atteindre", (byte)-117);
   int anInt2253;
   int anInt2254;
   static RSString aClass94_2255 = createRSString("Suche nach Updates )2 ", (byte)-126);
   int anInt2256;
   int anInt2257;
   static CacheIndex aClass153_2258;
   int anInt2259 = -1;
   static RSString aClass94_2260 = createRSString("Lade Wordpack )2 ", (byte)-117);
   int anInt2261 = 0;
   int anInt2262;
   int anInt2263;
   int anInt2264;
   int anInt2265;


   static final int method107(CacheIndex var0, byte var1) {
      try {
         int var2 = 0;
         if(var0.method2144(0, Class168.anInt2104)) {
            ++var2;
         }

         if(var0.method2144(0, Class3_Sub13_Sub23_Sub1.anInt4042)) {
            ++var2;
         }

         if(var0.method2144(0, client.anInt2195)) {
            ++var2;
         }

         if(var0.method2144(0, Node.anInt2575)) {
            ++var2;
         }

         if(var0.method2144(0, RenderAnimationDefinition.anInt380)) {
            ++var2;
         }

         if(var0.method2144(0, Class3_Sub13_Sub29.anInt3356)) {
            ++var2;
         }

         if(var0.method2144(0, Class129_Sub1.anInt2689)) {
            ++var2;
         }

         if(var1 > -124) {
            method109(68);
         }

         if(var0.method2144(0, Class3_Sub13_Sub4.anInt3061)) {
            ++var2;
         }

         if(var0.method2144(0, Class75_Sub1.anInt2633)) {
            ++var2;
         }

         if(var0.method2144(0, Class40.anInt678)) {
            ++var2;
         }

         if(var0.method2144(0, Class3_Sub15.anInt2436)) {
            ++var2;
         }

         if(var0.method2144(0, Class3_Sub28_Sub18.anInt3757)) {
            ++var2;
         }

         if(var0.method2144(0, Class45.anInt735)) {
            ++var2;
         }

         if(var0.method2144(0, Class93.anInt1325)) {
            ++var2;
         }

         if(var0.method2144(0, Class3_Sub18.anInt2471)) {
            ++var2;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "cd.B(" + (var0 != null?"{...}":"null") + ',' + var1 + ')');
      }
   }

   static final RSString createRSString(String var0, byte var1) {
      try {
         byte[] var2 = var0.getBytes(); 
         int var3 = var2.length;
         RSString var4 = new RSString();
         int var5 = 0;
         var4.aByteArray2153 = new byte[var3];

         while(var3 > var5) {
            int var6 = var2[var5++] & 255;
            if(45 >= var6 && ~var6 <= -41) {
               if(~var5 <= ~var3) {
                  break;
               }

               int var7 = 255 & var2[var5++];
               var4.aByteArray2153[var4.anInt2156++] = (byte)(-48 + var7 + 43 * (-40 + var6));
            } else if(~var6 != -1) {
               var4.aByteArray2153[var4.anInt2156++] = (byte)var6;
            }
         }

         if(var1 >= -116) {
            return (RSString)null;
         } else {
            var4.method1576((byte)90);
            return var4.method1571((byte)32);
         }
      } catch (RuntimeException var8) {
         throw Class44.method1067(var8, "cd.D(" + (var0 != null?"{...}":"null") + ',' + var1 + ')');
      }
   }
   static final RSString createRSString(String string) {
        try {
            byte[] var2 = string.getBytes();
            int var3 = var2.length;
            RSString var4 = new RSString();
            int var5 = 0;
            var4.aByteArray2153 = new byte[var3];

            while (var3 > var5) {
                int var6 = var2[var5++] & 255;
                if (45 >= var6 && ~var6 <= -41) {
                    if (~var5 <= ~var3) {
                        break;
                    }

                    int var7 = 255 & var2[var5++];
                    var4.aByteArray2153[var4.anInt2156++] = (byte) (-48 + var7 + 43 * (-40 + var6));
                } else if (~var6 != -1) {
                    var4.aByteArray2153[var4.anInt2156++] = (byte) var6;
                }
            }
            var4.method1576((byte) 90);
            return var4.method1571((byte) 32);
        } catch (RuntimeException var8) {
            throw Class44.method1067(var8, "cd.D(" + (string != null ? "{...}" : "null") + ',' + -1 + ')');
        }
    }
   public static void method109(int var0) {
      try {
         aClass94_2255 = null;
         aClass94_2260 = null;
         aClass94_2252 = null;
         if(var0 != 2) {
            method109(-22);
         }

         aClass153_2258 = null;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "cd.A(" + var0 + ')');
      }
   }
   //The variable var3 is Draw/Render distance.
   static final void method110(int var0, int var1, int var2, int var3, boolean var4) {
      IOHandler.anInt1234 = var1;
      Class3_Sub13_Sub15.anInt3179 = var2;
      Class3_Sub13_Sub39.anInt3466 = var3;
      Class3_Sub28_Sub10_Sub2.aClass3_Sub2ArrayArrayArray4070 = new Class3_Sub2[var0][IOHandler.anInt1234][Class3_Sub13_Sub15.anInt3179];
      Class58.anIntArrayArrayArray914 = new int[var0][IOHandler.anInt1234 + 1][Class3_Sub13_Sub15.anInt3179 + 1];
      if(Class138.highDetail) {
         client.aClass3_Sub11ArrayArray2199 = new Class3_Sub11[4][];
      }

      if(var4) {
         Class166.aClass3_Sub2ArrayArrayArray2065 = new Class3_Sub2[1][IOHandler.anInt1234][Class3_Sub13_Sub15.anInt3179];
         Class3_Sub13_Sub9.anIntArrayArray3115 = new int[IOHandler.anInt1234][Class3_Sub13_Sub15.anInt3179];
         Class3_Sub28_Sub7.anIntArrayArrayArray3605 = new int[1][IOHandler.anInt1234 + 1][Class3_Sub13_Sub15.anInt3179 + 1];
         if(Class138.highDetail) {
            Class3_Sub13_Sub28.aClass3_Sub11ArrayArray3346 = new Class3_Sub11[1][];
         }
      } else {
         Class166.aClass3_Sub2ArrayArrayArray2065 = (Class3_Sub2[][][])null;
         Class3_Sub13_Sub9.anIntArrayArray3115 = (int[][])null;
         Class3_Sub28_Sub7.anIntArrayArrayArray3605 = (int[][][])null;
         Class3_Sub13_Sub28.aClass3_Sub11ArrayArray3346 = (Class3_Sub11[][])null;
      }

      Class167.method2264(false);
      Class3_Sub28_Sub8.aClass113Array3610 = new Class113[500];
      anInt2249 = 0;
      Class145.aClass113Array1895 = new Class113[500];
      Class126.anInt1672 = 0;
      Class81.anIntArrayArrayArray1142 = new int[var0][IOHandler.anInt1234 + 1][Class3_Sub13_Sub15.anInt3179 + 1];
      Class142.aClass25Array1868 = new Class25[5000];
      Class3_Sub13_Sub5.anInt3070 = 0;
      Class3_Sub28_Sub10_Sub1.aClass25Array4060 = new Class25[100];
      Class23.aBooleanArrayArray457 = new boolean[Class3_Sub13_Sub39.anInt3466 + Class3_Sub13_Sub39.anInt3466 + 1][Class3_Sub13_Sub39.anInt3466 + Class3_Sub13_Sub39.anInt3466 + 1];
      Class49.aBooleanArrayArray814 = new boolean[Class3_Sub13_Sub39.anInt3466 + Class3_Sub13_Sub39.anInt3466 + 2][Class3_Sub13_Sub39.anInt3466 + Class3_Sub13_Sub39.anInt3466 + 2];
      Class136.aByteArrayArrayArray1774 = new byte[var0][IOHandler.anInt1234][Class3_Sub13_Sub15.anInt3179];
   }

}
