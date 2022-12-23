import java.util.Calendar;
import java.util.TimeZone;

final class Class3_Sub28_Sub5 extends Node {

   static CacheIndex aClass153_3580;
   static Calendar aCalendar3581 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
   private static RSString aClass94_3582 = Class3_Sub4.createRSString("K", (byte)-120);
   static RSString aClass94_3583 = Class3_Sub4.createRSString("brillant3:", (byte)-119);
   static RSString aClass94_3584 = aClass94_3582;
   static RSString aClass94_3585 = Class3_Sub4.createRSString(",Mcran)2titre ouvert", (byte)-124);
   static RSString aClass94_3586 = aClass94_3582;
   static int[] anIntArray3587;
   Class98[] aClass98Array3588;
   static int anInt3589;
   static int anInt3590 = -1;
   static int anInt3591 = 0;
   static int[] anIntArray3592 = new int[256];
   static volatile boolean aBoolean3593 = false;


   static final void method556(int var0, int var1, int var2, byte var3, int var4, int var5) {
      try {
         if(var3 < -93) {
            int var8 = var2 * var2;
            int var6 = 0;
            int var7 = var1;
            int var11 = var8 << 1;
            int var9 = var1 * var1;
            int var12 = var1 << 1;
            int var10 = var9 << 1;
            int var13 = var8 * (-var12 + 1) + var10;
            int var14 = -(var11 * (-1 + var12)) + var9;
            int var16 = var9 << 2;
            int var17 = var10 * ((var6 << 1) + 3);
            int var15 = var8 << 2;
            int var18 = ((var1 << 1) - 3) * var11;
            int var19 = var16 * (1 + var6);
            int var21;
            int var22;
            if(var5 >= Class159.anInt2020 && Class57.anInt902 >= var5) {
               var21 = Class40.method1040(Class3_Sub28_Sub18.anInt3765, var2 + var4, (byte)0, Class101.anInt1425);
               var22 = Class40.method1040(Class3_Sub28_Sub18.anInt3765, -var2 + var4, (byte)0, Class101.anInt1425);
               Class3_Sub13_Sub23_Sub1.method282(Class38.anIntArrayArray663[var5], var22, 91, var21, var0);
            }

            for(int var20 = var15 * (-1 + var1); 0 < var7; var20 -= var15) {
               --var7;
               if(-1 < ~var13) {
                  while(0 > var13) {
                     ++var6;
                     var13 += var17;
                     var14 += var19;
                     var19 += var16;
                     var17 += var16;
                  }
               }

               var21 = var5 - var7;
               if(var14 < 0) {
                  var14 += var19;
                  var13 += var17;
                  var17 += var16;
                  var19 += var16;
                  ++var6;
               }

               var14 += -var18;
               var18 -= var15;
               var13 += -var20;
               var22 = var7 + var5;
               if(~Class159.anInt2020 >= ~var22 && ~Class57.anInt902 <= ~var21) {
                  int var23 = Class40.method1040(Class3_Sub28_Sub18.anInt3765, var6 + var4, (byte)0, Class101.anInt1425);
                  int var24 = Class40.method1040(Class3_Sub28_Sub18.anInt3765, -var6 + var4, (byte)0, Class101.anInt1425);
                  if(var21 >= Class159.anInt2020) {
                     Class3_Sub13_Sub23_Sub1.method282(Class38.anIntArrayArray663[var21], var24, 121, var23, var0);
                  }

                  if(~Class57.anInt902 <= ~var22) {
                     Class3_Sub13_Sub23_Sub1.method282(Class38.anIntArrayArray663[var22], var24, -110, var23, var0);
                  }
               }
            }

         }
      } catch (RuntimeException var25) {
         throw Class44.method1067(var25, "cl.C(" + var0 + ',' + var1 + ',' + var2 + ',' + var3 + ',' + var4 + ',' + var5 + ')');
      }
   }

   static final long method557(int var0, int var1, int var2) {
      Class3_Sub2 var3 = Class75_Sub2.aClass3_Sub2ArrayArrayArray2638[var0][var1][var2];
      if(var3 == null) {
         return 0L;
      } else {
         for(int var4 = 0; var4 < var3.anInt2223; ++var4) {
            Class25 var5 = var3.aClass25Array2221[var4];
            if((var5.aLong498 >> 29 & 3L) == 2L && var5.anInt483 == var1 && var5.anInt478 == var2) {
               return var5.aLong498;
            }
         }

         return 0L;
      }
   }

   public static void method558(int var0) {
      try {
         aClass94_3585 = null;
         anIntArray3592 = null;
         aClass94_3582 = null;
         aClass94_3586 = null;
         aClass153_3580 = null;
         anIntArray3587 = null;
         if(var0 != -29679) {
            method556(-76, 24, -17, (byte)-85, 58, -87);
         }

         aClass94_3583 = null;
         aClass94_3584 = null;
         aCalendar3581 = null;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "cl.B(" + var0 + ')');
      }
   }

   final boolean method559(int var1, int var2) {
      try {
         return var1 != 1317095745?false:this.aClass98Array3588[var2].aBoolean1386;
      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "cl.D(" + var1 + ',' + var2 + ')');
      }
   }

   static final void method560(int var0) {
      try {
         for(int var1 = 0; ~var1 > -6; ++var1) {
            Class104.aBooleanArray2169[var1] = false;
         }

         if(var0 != -21556) {
            method556(21, 1, 64, (byte)40, -34, -70);
         }

         Class75.anInt1105 = 0;
         Class163_Sub2_Sub1.anInt4014 = 0;
         NPCDefinition.anInt1252 = -1;
         Class3_Sub7.anInt2293 = -1;
         Class133.anInt1753 = 1;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "cl.A(" + var0 + ')');
      }
   }

   Class3_Sub28_Sub5(CacheIndex var1, CacheIndex var2, int var3, boolean var4) {
      try {
         Class61 var5 = new Class61();
         int var6 = var1.method2142(var3, (byte)69);
         this.aClass98Array3588 = new Class98[var6];
         int[] var7 = var1.method2141((byte)-128, var3);
         int var8 = 0;

         while(~var8 > ~var7.length) {
            byte[] var9 = var1.getFile(var3, (byte)-122, var7[var8]);
            int var11 = 255 & var9[1] | (var9[0] & 255) << 8;
            Class3_Sub17 var12 = (Class3_Sub17)var5.method1222(-78);
            Class3_Sub17 var10 = null;

            while(true) {
               if(var12 != null) {
                  if(var12.anInt2454 != var11) {
                     var12 = (Class3_Sub17)var5.method1221(4);
                     continue;
                  }

                  var10 = var12;
               }

               if(null == var10) {
                  byte[] var13;
                  if(!var4) {
                     var13 = var2.method2140(0, var11, 0);
                  } else {
                     var13 = var2.method2140(var11, 0, 0);
                  }

                  var10 = new Class3_Sub17(var11, var13);
                  var5.method1215(true, var10);
               }

               this.aClass98Array3588[var7[var8]] = new Class98(var9, var10);
               ++var8;
               break;
            }
         }

      } catch (RuntimeException var14) {
         throw Class44.method1067(var14, "cl.<init>(" + (var1 != null?"{...}":"null") + ',' + (var2 != null?"{...}":"null") + ',' + var3 + ',' + var4 + ')');
      }
   }

   final boolean method561(int var1, byte var2) {
      try {
         return var2 < 114?true:this.aClass98Array3588[var1].aBoolean1382;
      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "cl.F(" + var1 + ',' + var2 + ')');
      }
   }

}
