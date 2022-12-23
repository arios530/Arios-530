
abstract class Class44 {

   static int anInt718 = 0;
   static int anInt719 = 0;
   static int[][][] anIntArrayArrayArray720;
   int countryIndex;
   int anInt722;
   static int[][][] anIntArrayArrayArray723;
   int settings;
   static Class93 aClass93_725 = new Class93(64);
   static int[] anIntArray726 = new int[32];
   static float aFloat727;


   static final boolean method1066(int var0, int var1) {
      try {
         if(97 <= var0 && ~var0 >= -123) {
            return true;
         } else {
            int var2 = 52 % ((56 - var1) / 43);
            return -66 >= ~var0 && 90 >= var0?true:48 <= var0 && 57 >= var0;
         }
      } catch (RuntimeException var3) {
         throw method1067(var3, "gj.K(" + var0 + ',' + var1 + ')');
      }
   }

   static final RuntimeException_Sub1 method1067(Throwable var0, String var1) {
//	  var0.printStackTrace();
      RuntimeException_Sub1 var2;
      if(!(var0 instanceof RuntimeException_Sub1)) {
         var2 = new RuntimeException_Sub1(var0, var1);
      } else {
         var2 = (RuntimeException_Sub1)var0;
         var2.aString2117 = var2.aString2117 + ' ' + var1;
      }

      return var2;
   }

   static final Class19 method1068(int var0, int var1, int var2) {
      Class3_Sub2 var3 = Class75_Sub2.aClass3_Sub2ArrayArrayArray2638[var0][var1][var2];
      return var3 == null?null:var3.aClass19_2233;
   }

   static final void method1069(long[] var0, int var1, int var2, int[] var3, int var4) {
      try {
         if(var4 != -24337) {
            method1067((Throwable)null, (String)null);
         }

         if(~var1 > ~var2) {
            int var6 = var1;
            int var5 = (var2 + var1) / 2;
            long var7 = var0[var5];
            var0[var5] = var0[var2];
            var0[var2] = var7;
            int var9 = var3[var5];
            var3[var5] = var3[var2];
            var3[var2] = var9;

            for(int var10 = var1; var2 > var10; ++var10) {
               if(var0[var10] < var7 - -((long)(1 & var10))) {
                  long var11 = var0[var10];
                  var0[var10] = var0[var6];
                  var0[var6] = var11;
                  int var13 = var3[var10];
                  var3[var10] = var3[var6];
                  var3[var6++] = var13;
               }
            }

            var0[var2] = var0[var6];
            var0[var6] = var7;
            var3[var2] = var3[var6];
            var3[var6] = var9;
            method1069(var0, var1, -1 + var6, var3, -24337);
            method1069(var0, 1 + var6, var2, var3, -24337);
         }

      } catch (RuntimeException var14) {
         throw method1067(var14, "gj.N(" + (var0 != null?"{...}":"null") + ',' + var1 + ',' + var2 + ',' + (var3 != null?"{...}":"null") + ',' + var4 + ')');
      }
   }

   final boolean method1070(int var1) {
      try {
         if(var1 != 8) {
            this.method1070(15);
         }

         return 0 != (1 & this.settings);
      } catch (RuntimeException var3) {
         throw method1067(var3, "gj.E(" + var1 + ')');
      }
   }

   public static void method1071(byte var0) {
      try {
         anIntArray726 = null;
         aClass93_725 = null;
         anIntArrayArrayArray723 = (int[][][])null;
         if(var0 >= -82) {
            method1071((byte)3);
         }

         anIntArrayArrayArray720 = (int[][][])null;
      } catch (RuntimeException var2) {
         throw method1067(var2, "gj.M(" + var0 + ')');
      }
   }

   final boolean method1072(boolean var1) {
      try {
         return var1?false:(this.settings & 4) != 0;
      } catch (RuntimeException var3) {
         throw method1067(var3, "gj.G(" + var1 + ')');
      }
   }

   static final void method1073(int var0) {
      try {
         Class3_Sub28_Sub4.method551(0, 0, 0);
         if(var0 != 97) {
            method1068(-108, 80, 18);
         }

      } catch (RuntimeException var2) {
         throw method1067(var2, "gj.L(" + var0 + ')');
      }
   }

   final boolean method1074(int var1) {
      try {
         if(var1 >= -106) {
            aFloat727 = -0.6283864F;
         }

         return ~(this.settings & 8) != -1;
      } catch (RuntimeException var3) {
         throw method1067(var3, "gj.I(" + var1 + ')');
      }
   }

   final boolean method1075(int var1) {
      try {
         if(var1 != 64) {
            this.settings = 51;
         }

         return ~(2 & this.settings) != -1;
      } catch (RuntimeException var3) {
         throw method1067(var3, "gj.H(" + var1 + ')');
      }
   }

}
