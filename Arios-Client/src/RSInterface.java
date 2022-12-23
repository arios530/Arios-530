
final class RSInterface {

   boolean aBoolean155 = false;
   Object[] anObjectArray156;
   boolean aBoolean157;
   static long aLong1489;
   Object[] anObjectArray158;
   Object[] anObjectArray159;
   int anInt160 = 1;
   Object[] anObjectArray161;
   byte aByte162 = 0;
   boolean aBoolean163;
   int anInt164 = 100;
   Object[] anObjectArray165;
   int anInt166;
   boolean aBoolean167;
   int anInt168;
   short aShort169 = 3000;
   Object[] anObjectArray170;
   RSString[] aClass94Array171;
   RSString aClass94_172;
   RSString[] aClass94Array173;
   Object[] anObjectArray174;
   int[] anIntArray175;
   Object[] anObjectArray176;
   int anInt177;
   boolean aBoolean178;
   int anInt179 = 0;
   Object[] anObjectArray180;
   boolean aBoolean181 = false;
   int anInt182 = 0;
   Object[] anObjectArray183;
   int anInt184;
   int[] anIntArray185;
   boolean aBoolean186 = false;
   int anInt187;
   boolean aBoolean188 = false;
   int anInt189;
   int anInt190;
   int anInt191 = -1;
   int anInt192;
   int anInt193 = 0;
   int anInt194 = 0;
   boolean aBoolean195;
   private int anInt196;
   int[] anIntArray197;
   int anInt198 = -1;
   boolean aBoolean199;
   boolean aBoolean200;
   int anInt201;
   int anInt202;
   Object[] anObjectArray203;
   int anInt204;
   int anInt205 = 0;
   Object[] anObjectArray206;
   int[] anIntArray207;
   int anInt208 = 0;
   static RSString aClass94_209 = Class3_Sub4.createRSString("event_opbase", (byte)-127);
   int anInt210 = 0;
   int[] anIntArray211;
   int anInt212;
   int anInt213;
   int anInt214 = 0;
   boolean aBoolean215;
   int anInt216;
   Object[] anObjectArray217;
   int anInt218;
   boolean aBoolean219;
   Object[] anObjectArray220;
   Object[] anObjectArray221;
   int anInt222;
   int anInt223;
   int anInt224 = -1;
   int anInt225;
   boolean aBoolean226 = false;
   boolean aBoolean227;
   int anInt228;
   Object[] anObjectArray229;
   int anInt230 = 0;
   byte[] aByteArray231;
   RSString aClass94_232;
   boolean aBoolean233;
   int anInt234;
   Object[] anObjectArray235;
   static boolean aBoolean236 = true;
   int anInt237;
   int anInt238 = -1;
   Object[] anObjectArray239;
   int anInt240;
   byte aByte241;
   int anInt242;
   RSString aClass94_243;
   int anInt244;
   RSString aClass94_245;
   static float aFloat246;
   int anInt247;
   Object[] anObjectArray248;
   int[] anIntArray249;
   int anInt250 = 1;
   static RSString aClass94_251 = null;
   int anInt252;
   int anInt253;
   int[] anIntArray254;
   int anInt255;
   Object[] anObjectArray256;
   Class3_Sub1 aClass3_Sub1_257;
   int anInt258;
   int anInt259;
   int anInt260;
   static long aLong261 = 0L;
   RSInterface[] aClass11Array262;
   byte[] aByteArray263;
   int anInt264;
   int anInt265;
   int anInt266;
   int anInt267;
   Object[] anObjectArray268;
   Object[] anObjectArray269;
   int anInt270;
   int anInt271;
   int[] anIntArray272;
   byte aByte273;
   int[] anIntArray274;
   int[] anIntArray275;
   Object[] anObjectArray276;
   RSString aClass94_277;
   static int anInt278 = -1;
   int anInt279;
   int anInt280;
   Object[] anObjectArray281;
   Object[] anObjectArray282;
   int anInt283;
   int anInt284;
   int anInt285;
   int[] anIntArray286;
   int anInt287;
   int anInt288;
   RSString aClass94_289;
   int anInt290;
   int[] anIntArray291;
   int anInt292;
   short aShort293;
   private int anInt294;
   Object[] anObjectArray295;
   int anInt296;
   static RSString aClass94_297 = Class3_Sub4.createRSString("Nehmen", (byte)-118);
   int[][] anIntArrayArray298;
   int[] anIntArray299;
   int[] anIntArray300;
   int anInt301;
   RSInterface aClass11_302;
   Object[] anObjectArray303;
   byte aByte304;
   int anInt305;
   int anInt306;
   int[] anIntArray307;
   int anInt308;
   boolean aBoolean309;
   int[] anIntArray310;
   int anInt311;
   int anInt312;
   Object[] anObjectArray313;
   Object[] anObjectArray314;
   Object[] anObjectArray315;
   int anInt316;
   int[] anIntArray317;
   int anInt318;


   final void method854(int var1, int var2, byte var3) {
      try {
         if(this.anIntArray249 == null || ~this.anIntArray249.length >= ~var1) {
            int[] var4 = new int[1 + var1];
            if(this.anIntArray249 != null) {
               int var5;
               for(var5 = 0; this.anIntArray249.length > var5; ++var5) {
                  var4[var5] = this.anIntArray249[var5];
               }

               for(var5 = this.anIntArray249.length; ~var1 < ~var5; ++var5) {
                  var4[var5] = -1;
               }
            }

            this.anIntArray249 = var4;
         }

         this.anIntArray249[var1] = var2;
         if(var3 != 43) {
            this.anIntArray211 = (int[])null;
         }

      } catch (RuntimeException var6) {
         throw Class44.method1067(var6, "be.P(" + var1 + ',' + var2 + ',' + var3 + ')');
      }
   }

   final boolean method855(int var1) {
      try {
         if(this.anIntArray207 != null) {
            return true;
         } else {
            Class109_Sub1 var2 = RSString.method1539(0, true, this.anInt224, Class12.aClass153_323);
            if(null == var2) {
               return false;
            } else {
               var2.method1675();
               this.anIntArray207 = new int[var2.anInt1468];
               this.anIntArray291 = new int[var2.anInt1468];
               int var3 = 0;

               while(~var3 > ~var2.anInt1468) {
                  int var4 = 0;
                  int var5 = var2.anInt1461;
                  int var6 = 0;

                  while(true) {
                     if(~var6 > ~var2.anInt1461) {
                        if(-1 == ~var2.aByteArray2674[var2.anInt1461 * var3 + var6]) {
                           ++var6;
                           continue;
                        }

                        var4 = var6;
                     }

                     for(var6 = var4; var2.anInt1461 > var6; ++var6) {
                        if(0 == var2.aByteArray2674[var3 * var2.anInt1461 + var6]) {
                           var5 = var6;
                           break;
                        }
                     }

                     this.anIntArray207[var3] = var4;
                     this.anIntArray291[var3] = var5 - var4;
                     ++var3;
                     break;
                  }
               }

               if(var1 != -30721) {
                  this.anInt205 = -68;
               }

               return true;
            }
         }
      } catch (RuntimeException var7) {
         throw Class44.method1067(var7, "be.G(" + var1 + ')');
      }
   }

   static final RSString method856(boolean var0) {
      try {
         if(!var0) {
            method869(127, -68);
         }

         RSString var1 = Class3_Sub28_Sub7_Sub1.aClass94_4052;
         RSString var2 = Class3_Sub28_Sub14.aClass94_3672;
         if(-1 != ~Class44.anInt718) {
            var1 = Player.aClass94_3971;
         }

         if(null != Class163_Sub2.aClass94_2996) {
            var2 = RenderAnimationDefinition.method903(new RSString[]{Class3_Sub28_Sub11.aClass94_3637, Class163_Sub2.aClass94_2996}, (byte)-64);
         }

         return RenderAnimationDefinition.method903(new RSString[]{Class30.aClass94_577, var1, Class3_Sub28_Sub7.aClass94_3601, Class72.method1298((byte)9, Class3_Sub20.language), Class151.aClass94_1932, Class72.method1298((byte)9, Class3_Sub26.anInt2554), var2, Class140_Sub3.aClass94_2735}, (byte)-61);
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "be.N(" + var0 + ')');
      }
   }

   final void method857(byte var1, RSString var2, int var3) {
      try {
         if(null == this.aClass94Array171 || ~this.aClass94Array171.length >= ~var3) {
            RSString[] var4 = new RSString[1 + var3];
            if(null != this.aClass94Array171) {
               for(int var5 = 0; ~this.aClass94Array171.length < ~var5; ++var5) {
                  var4[var5] = this.aClass94Array171[var5];
               }
            }

            this.aClass94Array171 = var4;
         }

         this.aClass94Array171[var3] = var2;
         int var7 = -124 % ((-10 - var1) / 60);
      } catch (RuntimeException var6) {
         throw Class44.method1067(var6, "be.B(" + var1 + ',' + (var2 != null?"{...}":"null") + ',' + var3 + ')');
      }
   }

   final void method858(int var1, RSByteBuffer var2) {
      try {
         if(var1 >= -94) {
            this.anInt214 = -74;
         }

         this.aBoolean233 = false;
         this.anInt187 = var2.getByte((byte)-33);
         this.anInt318 = var2.getByte((byte)-35);
         this.anInt189 = var2.getShort(1);
         this.anInt316 = var2.getShort((byte)100);
         this.anInt166 = var2.getShort((byte)109);
         this.anInt177 = var2.getShort(1);
         this.anInt244 = var2.getShort(1);
         this.aByte304 = 0;
         this.aByte241 = 0;
         this.aByte273 = 0;
         this.aByte162 = 0;
         this.anInt223 = var2.getByte((byte)-120);
         this.anInt190 = var2.getShort(1);
         if(~this.anInt190 != -65536) {
            this.anInt190 += -65536 & this.anInt279;
         } else {
            this.anInt190 = -1;
         }

         this.anInt212 = var2.getShort(1);
         if(-65536 == ~this.anInt212) {
            this.anInt212 = -1;
         }

         int var3 = var2.getByte((byte)-98);
         int var4;
         if(-1 > ~var3) {
            this.anIntArray307 = new int[var3];
            this.anIntArray275 = new int[var3];

            for(var4 = 0; ~var3 < ~var4; ++var4) {
               this.anIntArray275[var4] = var2.getByte((byte)-102);
               this.anIntArray307[var4] = var2.getShort(1);
            }
         }

         var4 = var2.getByte((byte)-46);
         int var5;
         int var6;
         int var7;
         if(-1 > ~var4) {
            this.anIntArrayArray298 = new int[var4][];

            for(var5 = 0; ~var4 < ~var5; ++var5) {
               var6 = var2.getShort(1);
               this.anIntArrayArray298[var5] = new int[var6];

               for(var7 = 0; ~var7 > ~var6; ++var7) {
                  this.anIntArrayArray298[var5][var7] = var2.getShort(1);
                  if(~this.anIntArrayArray298[var5][var7] == -65536) {
                     this.anIntArrayArray298[var5][var7] = -1;
                  }
               }
            }
         }

         if(-1 == ~this.anInt187) {
            this.anInt252 = var2.getShort(1);
            this.aBoolean155 = 1 == var2.getByte((byte)-67);
         }

         if(~this.anInt187 == -2) {
            var2.getShort(1);
            var2.getByte((byte)-67);
         }

         var5 = 0;
         if(~this.anInt187 == -3) {
            this.aByte241 = 3;
            this.anIntArray317 = new int[this.anInt177 * this.anInt244];
            this.anIntArray254 = new int[this.anInt244 * this.anInt177];
            this.aByte304 = 3;
            var6 = var2.getByte((byte)-58);
            var7 = var2.getByte((byte)-115);
            if(~var6 == -2) {
               var5 |= 268435456;
            }

            int var8 = var2.getByte((byte)-97);
            if(~var7 == -2) {
               var5 |= 1073741824;
            }

            if(1 == var8) {
               var5 |= Integer.MIN_VALUE;
            }

            int var9 = var2.getByte((byte)-102);
            if(var9 == 1) {
               var5 |= 536870912;
            }

            this.anInt285 = var2.getByte((byte)-125);
            this.anInt290 = var2.getByte((byte)-50);
            this.anIntArray300 = new int[20];
            this.anIntArray272 = new int[20];
            this.anIntArray197 = new int[20];

            int var10;
            for(var10 = 0; 20 > var10; ++var10) {
               int var11 = var2.getByte((byte)-48);
               if(var11 == 1) {
                  this.anIntArray272[var10] = var2.getShort((byte)110);
                  this.anIntArray300[var10] = var2.getShort((byte)58);
                  this.anIntArray197[var10] = var2.getInt(-502942936);
               } else {
                  this.anIntArray197[var10] = -1;
               }
            }

            this.aClass94Array173 = new RSString[5];

            for(var10 = 0; var10 < 5; ++var10) {
               RSString var14 = var2.getString();
               if(~var14.length(-28) < -1) {
                  this.aClass94Array173[var10] = var14;
                  var5 |= 1 << 23 - -var10;
               }
            }
         }

         if(3 == this.anInt187) {
            this.aBoolean226 = 1 == var2.getByte((byte)-106);
         }

         if(this.anInt187 == 4 || 1 == this.anInt187) {
            this.anInt194 = var2.getByte((byte)-31);
            this.anInt225 = var2.getByte((byte)-23);
            this.anInt205 = var2.getByte((byte)-35);
            this.anInt270 = var2.getShort(1);
            if(~this.anInt270 == -65536) {
               this.anInt270 = -1;
            }

            this.aBoolean215 = 1 == var2.getByte((byte)-114);
         }

         if(this.anInt187 == 4) {
            this.aClass94_232 = var2.getString();
            this.aClass94_172 = var2.getString();
         }

         if(this.anInt187 == 1 || this.anInt187 == 3 || 4 == this.anInt187) {
            this.anInt218 = var2.getInt(-502942936);
         }

         if(~this.anInt187 == -4 || ~this.anInt187 == -5) {
            this.anInt253 = var2.getInt(-502942936);
            this.anInt228 = var2.getInt(-502942936);
            this.anInt222 = var2.getInt(-502942936);
         }

         if(-6 == ~this.anInt187) {
            this.anInt224 = var2.getInt(-502942936);
            this.anInt296 = var2.getInt(-502942936);
         }

         if(6 == this.anInt187) {
            this.anInt202 = 1;
            this.anInt201 = var2.getShort(1);
            this.anInt294 = 1;
            if(this.anInt201 == '\uffff') {
               this.anInt201 = -1;
            }

            this.anInt196 = var2.getShort(1);
            if(this.anInt196 == '\uffff') {
               this.anInt196 = -1;
            }

            this.anInt305 = var2.getShort(1);
            if(~this.anInt305 == -65536) {
               this.anInt305 = -1;
            }

            this.anInt198 = var2.getShort(1);
            if('\uffff' == this.anInt198) {
               this.anInt198 = -1;
            }

            this.anInt164 = var2.getShort(1);
            this.anInt182 = var2.getShort(1);
            this.anInt308 = var2.getShort(1);
         }

         if(7 == this.anInt187) {
            this.aByte241 = 3;
            this.aByte304 = 3;
            this.anIntArray317 = new int[this.anInt244 * this.anInt177];
            this.anIntArray254 = new int[this.anInt177 * this.anInt244];
            this.anInt194 = var2.getByte((byte)-95);
            this.anInt270 = var2.getShort(1);
            if(~this.anInt270 == -65536) {
               this.anInt270 = -1;
            }

            this.aBoolean215 = ~var2.getByte((byte)-128) == -2;
            this.anInt218 = var2.getInt(-502942936);
            this.anInt285 = var2.getShort((byte)31);
            this.anInt290 = var2.getShort((byte)83);
            var6 = var2.getByte((byte)-74);
            if(-2 == ~var6) {
               var5 |= 1073741824;
            }

            this.aClass94Array173 = new RSString[5];

            for(var7 = 0; var7 < 5; ++var7) {
               RSString var13 = var2.getString();
               if(var13.length(-121) > 0) {
                  this.aClass94Array173[var7] = var13;
                  var5 |= 1 << 23 - -var7;
               }
            }
         }

         if(8 == this.anInt187) {
            this.aClass94_232 = var2.getString();
         }

         if(-3 == ~this.anInt318 || ~this.anInt187 == -3) {
            this.aClass94_245 = var2.getString();
            this.aClass94_243 = var2.getString();
            var6 = 63 & var2.getShort(1);
            var5 |= var6 << 11;
         }

         if(this.anInt318 == 1 || this.anInt318 == 4 || -6 == ~this.anInt318 || this.anInt318 == 6) {
            this.aClass94_289 = var2.getString();
            if(this.aClass94_289.length(-33) == 0) {
               if(~this.anInt318 == -2) {
                  this.aClass94_289 = Class115.aClass94_1583;
               }

               if(-5 == ~this.anInt318) {
                  this.aClass94_289 = Class131.aClass94_1722;
               }

               if(5 == this.anInt318) {
                  this.aClass94_289 = Class131.aClass94_1722;
               }

               if(this.anInt318 == 6) {
                  this.aClass94_289 = Class60.aClass94_935;
               }
            }
         }

         if(-2 == ~this.anInt318 || -5 == ~this.anInt318 || -6 == ~this.anInt318) {
            var5 |= 4194304;
         }

         if(~this.anInt318 == -7) {
            var5 |= 1;
         }

         this.aClass3_Sub1_257 = new Class3_Sub1(var5, -1);
      } catch (RuntimeException var12) {
         throw Class44.method1067(var12, "be.M(" + var1 + ',' + (var2 != null?"{...}":"null") + ')');
      }
   }

   final Class3_Sub28_Sub16 method859(boolean var1, int var2) {
      try {
         Applet_Sub1.aBoolean6 = false;
         if(!var1) {
            return (Class3_Sub28_Sub16)null;
         } else if(~var2 <= -1 && var2 < this.anIntArray197.length) {
            int var3 = this.anIntArray197[var2];
            if(~var3 != 0) {
               Class3_Sub28_Sub16 var4 = (Class3_Sub28_Sub16)Class114.aClass93_1569.get((long)var3, (byte)121);
               if(var4 == null) {
                  var4 = Class3_Sub28_Sub11.method602(0, var3, (byte)-18, Class12.aClass153_323);
                  if(null != var4) {
                     Class114.aClass93_1569.put((byte)-126, var4, (long)var3);
                  } else {
                     Applet_Sub1.aBoolean6 = true;
                  }

                  return var4;
               } else {
                  return var4;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      } catch (RuntimeException var5) {
         throw Class44.method1067(var5, "be.I(" + var1 + ',' + var2 + ')');
      }
   }

   public static void method860(int var0) {
      try {
         aClass94_297 = null;
         aClass94_209 = null;
         if(var0 < 63) {
            method860(42);
         }

         aClass94_251 = null;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "be.F(" + var0 + ')');
      }
   }

   static final int method861(int var0, int var1, int var2) {
      try {
         Class3_Sub25 var3 = (Class3_Sub25)Class3_Sub2.aClass130_2220.method1780((long)var0, 0);
         return null == var3?-1:(0 <= var2 && var2 < var3.anIntArray2547.length?(var1 < 39?-69:var3.anIntArray2547[var2]):-1);
      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "be.J(" + var0 + ',' + var1 + ',' + var2 + ')');
      }
   }

   private final Object[] method862(int var1, RSByteBuffer var2) {
      try {
         if(var1 != -65536) {
            return (Object[])null;
         } else {
            int var3 = var2.getByte((byte)-103);
            if(-1 != ~var3) {
               Object[] var4 = new Object[var3];

               for(int var5 = 0; var3 > var5; ++var5) {
                  int var6 = var2.getByte((byte)-115);
                  if(0 != var6) {
                     if(-2 == ~var6) {
                        var4[var5] = var2.getString();
                     }
                  } else {
                     var4[var5] = new Integer(var2.getInt(-502942936));
                  }
               }

               this.aBoolean195 = true;
               return var4;
            } else {
               return null;
            }
         }
      } catch (RuntimeException var7) {
         throw Class44.method1067(var7, "be.K(" + var1 + ',' + (var2 != null?"{...}":"null") + ')');
      }
   }

   private final int[] method863(RSByteBuffer var1, boolean var2) {
      try {
         int var3 = var1.getByte((byte)-125);
         if(-1 == ~var3) {
            return null;
         } else {
            int[] var4 = new int[var3];
            if(var2) {
               this.anInt312 = 20;
            }

            for(int var5 = 0; ~var5 > ~var3; ++var5) {
               var4[var5] = var1.getInt(-502942936);
            }

            return var4;
         }
      } catch (RuntimeException var6) {
         throw Class44.method1067(var6, "be.H(" + (var1 != null?"{...}":"null") + ',' + var2 + ')');
      }
   }

   final void method864(int var1, int var2, int var3) {
      try {
         int var4 = this.anIntArray254[var2];
         this.anIntArray254[var2] = this.anIntArray254[var1];
         if(var3 > -66) {
            this.method858(36, (RSByteBuffer)null);
         }

         this.anIntArray254[var1] = var4;
         var4 = this.anIntArray317[var2];
         this.anIntArray317[var2] = this.anIntArray317[var1];
         this.anIntArray317[var1] = var4;
      } catch (RuntimeException var5) {
         throw Class44.method1067(var5, "be.L(" + var1 + ',' + var2 + ',' + var3 + ')');
      }
   }

   final Class140_Sub1 method865(int var1, Class142 var2, int var3, int var4, int var5, boolean var6, Class52 var7) {
      try {
         Applet_Sub1.aBoolean6 = false;
         int var8;
         int var9;
         if(var6) {
            var8 = this.anInt294;
            var9 = this.anInt196;
         } else {
            var9 = this.anInt201;
            var8 = this.anInt202;
         }

         if(var4 < 125) {
            return (Class140_Sub1)null;
         } else if(-1 != ~var8) {
            if(-2 == ~var8 && var9 == -1) {
               return null;
            } else {
               Class140_Sub1 var10;
               if(1 == var8) {
                  var10 = (Class140_Sub1)Class3_Sub15.aClass93_2428.get((long)((var8 << 16) - -var9), (byte)121);
                  if(var10 == null) {
                     Class140_Sub5 var18 = Class140_Sub5.method2015(Class119.aClass153_1628, var9, 0);
                     if(var18 == null) {
                        Applet_Sub1.aBoolean6 = true;
                        return null;
                     }

                     var10 = var18.method2008(64, 768, -50, -10, -50);
                     Class3_Sub15.aClass93_2428.put((byte)-115, var10, (long)(var9 + (var8 << 16)));
                  }

                  if(var2 != null) {
                     var10 = var2.method2055(var10, (byte)119, var1, var5, var3);
                  }

                  return var10;
               } else if(var8 != 2) {
                  if(3 != var8) {
                     if(4 == var8) {
                        ItemDefinition var16 = Class38.getItemDefinition(var9, (byte)94);
                        Class140_Sub1 var17 = var16.method1110(110, var1, var5, var2, 10, var3);
                        if(var17 != null) {
                           return var17;
                        } else {
                           Applet_Sub1.aBoolean6 = true;
                           return null;
                        }
                     } else if(var8 != 6) {
                        if(~var8 != -8) {
                           return null;
                        } else if(var7 != null) {
                           int var15 = this.anInt201 >>> 16;
                           int var11 = this.anInt201 & '\uffff';
                           int var12 = this.anInt265;
                           Class140_Sub1 var13 = var7.method1157(var1, var12, var15, var5, var2, var3, var11, -2012759707);
                           if(var13 == null) {
                              Applet_Sub1.aBoolean6 = true;
                              return null;
                           } else {
                              return var13;
                           }
                        } else {
                           return null;
                        }
                     } else {
                        var10 = Node.method522(var9, 27112).method1476((Class145[])null, 0, (byte)-120, 0, var1, var5, var3, (Class142)null, 0, var2);
                        if(null != var10) {
                           return var10;
                        } else {
                           Applet_Sub1.aBoolean6 = true;
                           return null;
                        }
                     }
                  } else if(null == var7) {
                     return null;
                  } else {
                     var10 = var7.method1167(var5, (byte)127, var2, var3, var1);
                     if(null == var10) {
                        Applet_Sub1.aBoolean6 = true;
                        return null;
                     } else {
                        return var10;
                     }
                  }
               } else {
                  var10 = Node.method522(var9, 27112).method1482(var2, var5, var1, 27, var3);
                  if(null != var10) {
                     return var10;
                  } else {
                     Applet_Sub1.aBoolean6 = true;
                     return null;
                  }
               }
            }
         } else {
            return null;
         }
      } catch (RuntimeException var14) {
         throw Class44.method1067(var14, "be.E(" + var1 + ',' + (var2 != null?"{...}":"null") + ',' + var3 + ',' + var4 + ',' + var5 + ',' + var6 + ',' + (var7 != null?"{...}":"null") + ')');
      }
   }

   final Class3_Sub28_Sub16 method866(byte var1, boolean var2) {
      try {
         Applet_Sub1.aBoolean6 = false;
         int var3;
         if(var2) {
            var3 = this.anInt296;
         } else {
            var3 = this.anInt224;
         }

         if(0 == ~var3) {
            return null;
         } else {
            long var4 = ((this.aBoolean178?1L:0L) << 38) + ((!this.aBoolean157?0L:1L) << 35) + (long)var3 + ((long)this.anInt288 << 36) + ((this.aBoolean199?1L:0L) << 39) + ((long)this.anInt287 << 40);
            Class3_Sub28_Sub16 var6 = (Class3_Sub28_Sub16)Class114.aClass93_1569.get(var4, (byte)121);
            if(var6 != null) {
               return var6;
            } else {
               Class3_Sub28_Sub16_Sub2 var7;
               if(this.aBoolean157) {
                  var7 = Class3_Sub28_Sub7.method562(Class12.aClass153_323, 0, var3, (byte)39);
               } else {
                  var7 = Class40.method1043(0, Class12.aClass153_323, -3178, var3);
               }

               if(null == var7) {
                  Applet_Sub1.aBoolean6 = true;
                  return null;
               } else if(var1 != -113) {
                  return (Class3_Sub28_Sub16)null;
               } else {
                  if(this.aBoolean178) {
                     var7.method663();
                  }

                  if(this.aBoolean199) {
                     var7.method653();
                  }

                  if(this.anInt288 > 0) {
                     var7.method652(this.anInt288);
                  }

                  if(~this.anInt288 <= -2) {
                     var7.method657(1);
                  }

                  if(2 <= this.anInt288) {
                     var7.method657(16777215);
                  }

                  if(this.anInt287 != 0) {
                     var7.method668(this.anInt287);
                  }

                  Object var9;
                  if(Class138.highDetail) {
                     if(!(var7 instanceof Class3_Sub28_Sub16_Sub2_Sub1)) {
                        var9 = new Class3_Sub28_Sub16_Sub1(var7);
                     } else {
                        var9 = new Class3_Sub28_Sub16_Sub1_Sub1(var7);
                     }
                  } else {
                     var9 = var7;
                  }

                  Class114.aClass93_1569.put((byte)-75, var9, var4);
                  return (Class3_Sub28_Sub16)var9;
               }
            }
         }
      } catch (RuntimeException var8) {
         throw Class44.method1067(var8, "be.O(" + var1 + ',' + var2 + ')');
      }
   }

   final void method867(int var1, RSByteBuffer var2) {
      try {
         this.aBoolean233 = true;
         ++var2.index;
         this.anInt187 = var2.getByte((byte)-120);
         if(-1 != ~(128 & this.anInt187)) {
            this.anInt187 &= 127;
            var2.getString();
         }

         this.anInt189 = var2.getShort(var1 + 2);
         this.anInt316 = var2.getShort((byte)66);
         this.anInt166 = var2.getShort((byte)121);
         this.anInt177 = var2.getShort(1);
         this.anInt244 = var2.getShort(1);
         this.aByte304 = var2.getByte(false);
         this.aByte241 = var2.getByte(false);
         this.aByte273 = var2.getByte(false);
         this.aByte162 = var2.getByte(false);
         this.anInt190 = var2.getShort(var1 ^ -2);
         if(-65536 == ~this.anInt190) {
            this.anInt190 = -1;
         } else {
            this.anInt190 = (this.anInt279 & -65536) - -this.anInt190;
         }

         this.aBoolean155 = -2 == ~var2.getByte((byte)-40);
         if(~this.anInt187 == -1) {
            this.anInt240 = var2.getShort(1);
            this.anInt252 = var2.getShort(1);
            this.aBoolean219 = -2 == ~var2.getByte((byte)-114);
         }

         int var3;
         if(~this.anInt187 == -6) {
            this.anInt224 = var2.getInt(var1 + -502942935);
            this.anInt301 = var2.getShort(1);
            var3 = var2.getByte((byte)-39);
            this.aBoolean157 = -1 != ~(2 & var3);
            this.aBoolean186 = ~(1 & var3) != -1;
            this.anInt223 = var2.getByte((byte)-82);
            this.anInt288 = var2.getByte((byte)-86);
            this.anInt287 = var2.getInt(-502942936);
            this.aBoolean178 = ~var2.getByte((byte)-93) == -2;
            this.aBoolean199 = 1 == var2.getByte((byte)-60);
         }

         if(~this.anInt187 == -7) {
            this.anInt202 = 1;
            this.anInt201 = var2.getShort(var1 + 2);
            if(~this.anInt201 == -65536) {
               this.anInt201 = -1;
            }

            this.anInt259 = var2.getShort((byte)122);
            this.anInt230 = var2.getShort((byte)32);
            this.anInt182 = var2.getShort(1);
            this.anInt308 = var2.getShort(1);
            this.anInt280 = var2.getShort(1);
            this.anInt164 = var2.getShort(1);
            this.anInt305 = var2.getShort(1);
            if('\uffff' == this.anInt305) {
               this.anInt305 = -1;
            }

            this.aBoolean181 = var2.getByte((byte)-100) == 1;
            this.aShort293 = (short)var2.getShort(1);
            this.aShort169 = (short)var2.getShort(1);
            this.aBoolean309 = 1 == var2.getByte((byte)-64);
            if(this.aByte304 != 0) {
               this.anInt184 = var2.getShort(1);
            }

            if(this.aByte241 != 0) {
               this.anInt312 = var2.getShort(1);
            }
         }

         if(~this.anInt187 == -5) {
            this.anInt270 = var2.getShort(1);
            if(~this.anInt270 == -65536) {
               this.anInt270 = -1;
            }

            this.aClass94_232 = var2.getString();
            this.anInt205 = var2.getByte((byte)-124);
            this.anInt194 = var2.getByte((byte)-75);
            this.anInt225 = var2.getByte((byte)-38);
            this.aBoolean215 = var2.getByte((byte)-51) == 1;
            this.anInt218 = var2.getInt(-502942936);
         }

         if(this.anInt187 == 3) {
            this.anInt218 = var2.getInt(var1 + -502942935);
            this.aBoolean226 = 1 == var2.getByte((byte)-90);
            this.anInt223 = var2.getByte((byte)-63);
         }

         if(-10 == ~this.anInt187) {
            this.anInt250 = var2.getByte((byte)-56);
            this.anInt218 = var2.getInt(-502942936);
            this.aBoolean167 = 1 == var2.getByte((byte)-61);
         }

         var3 = var2.getTriByte((byte)87);
         int var4 = var2.getByte((byte)-91);
         int var5;
         if(var4 != 0) {
            this.anIntArray299 = new int[10];
            this.aByteArray263 = new byte[10];

            for(this.aByteArray231 = new byte[10]; ~var4 != -1; var4 = var2.getByte((byte)-80)) {
               var5 = (var4 >> 4) - 1;
               var4 = var2.getByte((byte)-63) | var4 << 8;
               var4 &= 4095;
               if(4095 == var4) {
                  this.anIntArray299[var5] = -1;
               } else {
                  this.anIntArray299[var5] = var4;
               }

               this.aByteArray263[var5] = var2.getByte(false);
               this.aByteArray231[var5] = var2.getByte(false);
            }
         }

         this.aClass94_277 = var2.getString();
         var5 = var2.getByte((byte)-80);
         int var6 = var5 & 15;
         int var8;
         if(0 < var6) {
            this.aClass94Array171 = new RSString[var6];

            for(var8 = 0; var6 > var8; ++var8) {
               this.aClass94Array171[var8] = var2.getString();
            }
         }

         int var7 = var5 >> 4;
         if(var7 > 0) {
            var8 = var2.getByte((byte)-93);
            this.anIntArray249 = new int[var8 + 1];

            for(int var9 = 0; var9 < this.anIntArray249.length; ++var9) {
               this.anIntArray249[var9] = -1;
            }

            this.anIntArray249[var8] = var2.getShort(1);
         }

         if(1 < var7) {
            var8 = var2.getByte((byte)-33);
            this.anIntArray249[var8] = var2.getShort(1);
         }

         this.anInt214 = var2.getByte((byte)-105);
         this.anInt179 = var2.getByte((byte)-78);
         this.aBoolean200 = var2.getByte((byte)-119) == 1;
         var8 = var1;
         this.aClass94_245 = var2.getString();
         if(0 != Class3_Sub28_Sub15.method630((byte)-34, var3)) {
            var8 = var2.getShort(1);
            this.anInt266 = var2.getShort(1);
            if(-65536 == ~var8) {
               var8 = -1;
            }

            if('\uffff' == this.anInt266) {
               this.anInt266 = -1;
            }

            this.anInt238 = var2.getShort(1);
            if(this.anInt238 == '\uffff') {
               this.anInt238 = -1;
            }
         }

         this.aClass3_Sub1_257 = new Class3_Sub1(var3, var8);
         this.anObjectArray159 = this.method862(-65536, var2);
         this.anObjectArray248 = this.method862(var1 + -65535, var2);
         this.anObjectArray281 = this.method862(-65536, var2);
         this.anObjectArray303 = this.method862(var1 ^ '\uffff', var2);
         this.anObjectArray203 = this.method862(-65536, var2);
         this.anObjectArray282 = this.method862(var1 ^ '\uffff', var2);
         this.anObjectArray174 = this.method862(var1 + -65535, var2);
         this.anObjectArray158 = this.method862(-65536, var2);
         this.anObjectArray269 = this.method862(-65536, var2);
         this.anObjectArray314 = this.method862(var1 ^ '\uffff', var2);
         this.anObjectArray276 = this.method862(-65536, var2);
         this.anObjectArray165 = this.method862(-65536, var2);
         this.anObjectArray170 = this.method862(var1 ^ '\uffff', var2);
         this.anObjectArray239 = this.method862(var1 ^ '\uffff', var2);
         this.anObjectArray180 = this.method862(-65536, var2);
         this.anObjectArray295 = this.method862(-65536, var2);
         this.anObjectArray229 = this.method862(-65536, var2);
         this.anObjectArray183 = this.method862(-65536, var2);
         this.anObjectArray161 = this.method862(-65536, var2);
         this.anObjectArray221 = this.method862(-65536, var2);
         this.anIntArray286 = this.method863(var2, false);
         this.anIntArray175 = this.method863(var2, false);
         this.anIntArray274 = this.method863(var2, false);
         this.anIntArray211 = this.method863(var2, false);
         this.anIntArray185 = this.method863(var2, false);
      } catch (RuntimeException var10) {
         throw Class44.method1067(var10, "be.C(" + var1 + ',' + (var2 != null?"{...}":"null") + ')');
      }
   }

   final Class3_Sub28_Sub17 method868(Class109[] var1, int var2) {
      try {
         Applet_Sub1.aBoolean6 = false;
         if(0 == ~this.anInt270) {
            return null;
         } else {
            Class3_Sub28_Sub17 var3 = (Class3_Sub28_Sub17)Class47.aClass93_743.get((long)this.anInt270, (byte)121);
            if(null != var3) {
               return var3;
            } else {
               var3 = Class73.method1300(var2, this.anInt270, (byte)127, Class12.aClass153_323, Class97.aClass153_1378);
               if(null == var3) {
                  Applet_Sub1.aBoolean6 = true;
               } else {
                  var3.method697(var1, (int[])null);
                  Class47.aClass93_743.put((byte)-77, var3, (long)this.anInt270);
               }

               return var3;
            }
         }
      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "be.A(" + (var1 != null?"{...}":"null") + ',' + var2 + ')');
      }
   }

   static final int method869(int var0, int var1) {
      try {
         return ~var1 != -16711936?(var0 < 97?-63:Class56.method1186(0, var1)):-1;
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "be.D(" + var0 + ',' + var1 + ')');
      }
   }

   public RSInterface() {
      this.aClass94_243 = Class104.aClass94_2171;
      this.aBoolean163 = false;
      this.anInt225 = 0;
      this.anInt212 = -1;
      this.aBoolean167 = false;
      this.anInt266 = -1;
      this.aByte241 = 0;
      this.anInt252 = 0;
      this.aBoolean200 = false;
      this.aBoolean215 = false;
      this.anInt204 = -1;
      this.anInt260 = 1;
      this.anInt228 = 0;
      this.aBoolean233 = false;
      this.aClass3_Sub1_257 = Class158_Sub1.aClass3_Sub1_2980;
      this.anInt253 = 0;
      this.aClass94_232 = Class104.aClass94_2171;
      this.anInt168 = 0;
      this.anInt247 = 0;
      this.aBoolean219 = false;
      this.anInt196 = -1;
      this.anInt190 = -1;
      this.anInt216 = 1;
      this.anInt192 = -1;
      this.anInt222 = 0;
      this.anInt264 = 0;
      this.aClass94_277 = Class104.aClass94_2171;
      this.anInt284 = 0;
      this.anInt177 = 0;
      this.anInt285 = 0;
      this.anInt234 = -1;
      this.aBoolean157 = false;
      this.anInt184 = 0;
      this.anInt223 = 0;
      this.anInt258 = 0;
      this.aClass94_245 = Class104.aClass94_2171;
      this.anInt237 = 0;
      this.aClass94_172 = Class104.aClass94_2171;
      this.anInt288 = 0;
      this.anInt265 = -1;
      this.anInt242 = 0;
      this.anInt259 = 0;
      this.anInt290 = 0;
      this.anInt244 = 0;
      this.anInt279 = -1;
      this.anInt296 = -1;
      this.aByte273 = 0;
      this.anInt267 = 0;
      this.anInt270 = -1;
      this.anInt240 = 0;
      this.anInt255 = 0;
      this.aShort293 = 0;
      this.anInt301 = 0;
      this.anInt305 = -1;
      this.aClass94_289 = Class115.aClass94_1583;
      this.anInt280 = 0;
      this.anInt271 = 0;
      this.anInt292 = -1;
      this.anInt189 = 0;
      this.anInt287 = 0;
      this.aClass11_302 = null;
      this.anInt311 = 0;
      this.anInt202 = 1;
      this.aBoolean309 = false;
      this.aByte304 = 0;
      this.anInt294 = 1;
      this.anInt312 = 0;
      this.anInt308 = 0;
      this.aBoolean195 = false;
      this.anInt316 = 0;
      this.anInt306 = 0;
      this.anInt166 = 0;
      this.aBoolean227 = true;
      this.anInt283 = 0;
      this.anInt213 = 0;
      this.anInt218 = 0;
      this.anInt318 = 0;
   }

}
