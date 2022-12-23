
final class Player extends Class140_Sub4 {

   static int[] anIntArray3951 = new int[4];
   int anInt3952 = -1;
   static byte aByte3953;
   static int[] anIntArray3954 = new int[100];
   int headIcon = -1;
   int anInt2830 = 0;
   static RSString aClass94_3957 = Class3_Sub4.createRSString("Gestionnaire de saisie charg-B", (byte)-117);
   private int anInt3958 = 0;
   static int[] anIntArray3959 = new int[2];
   int anInt3960 = 0;
   static RSString aClass94_3961 = Class3_Sub4.createRSString("Forced tweening ENABLED(Q", (byte)-118);
   Class52 aClass52_3962;
   int anInt3963 = -1;
   static RSString aClass94_3964 = Class3_Sub4.createRSString("_labels", (byte)-121);
   int anInt3965 = 0;
   int anInt3966 = -1;
   RSString displayName;
   boolean aBoolean3968 = false;
   int anInt3969 = 0;
   int anInt3970 = -1;
   static RSString aClass94_3971 = Class3_Sub4.createRSString("www)2wtqa", (byte)-125);
   int skullIcon = -1;
   int anInt3973 = -1;
   int anInt3974 = 0;


   final int getSize(byte var1) {
      try {
         if(null != this.aClass52_3962 && 0 != ~this.aClass52_3962.pnpcId) {
            return Node.method522(this.aClass52_3962.pnpcId, var1 + 26998).size;
         } else {
            if(var1 != 114) {
               this.method1966((byte)-22);
            }

            return super.getSize((byte)114);
         }
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "e.H(" + var1 + ')');
      }
   }

   final int getRenderAnimationId(int var1) {
      try {
         if(var1 != -1) {
            this.finalize();
         }

         return this.renderAnimationId;
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "e.B(" + var1 + ')');
      }
   }

   final void parseAppearance(int var1, RSByteBuffer buffer) {
      try {
         buffer.index = 0;
         int var5 = -63 / ((36 - var1) / 48);
         int var3 = buffer.getByte((byte)-105);
         int npcId = -1;
         int var4 = 1 & var3;
         boolean var6 = ~(var3 & 4) != -1;
         int var7 = super.getSize((byte)114);
         int[] var9 = new int[12];
         this.setSize(1 + (var3 >> 3 & 7), 2);
         this.anInt3958 = 3 & var3 >> 6;
         this.anInt2819 += (-var7 + this.getSize((byte)114)) * 64;
         this.anInt2829 += 64 * (this.getSize((byte)114) + -var7);
         this.skullIcon = buffer.getByte(false);
         this.headIcon = buffer.getByte(false);
         this.anInt2830 = 0;

         int var11;
         int var12;
         int var13;
         int var14;
         for(int var10 = 0; -13 < ~var10; ++var10) {
            var11 = buffer.getByte((byte)-29);
            if(~var11 != -1) {
               var12 = buffer.getByte((byte)-109);
               var13 = (var11 << 8) + var12;
               if(~var10 == -1 && -65536 == ~var13) {
                  npcId = buffer.getShort(1);
                  this.anInt2830 = buffer.getByte((byte)-24);
                  break;
               }

               if('\u8000' <= var13) {
                  var13 = Class75_Sub4.anIntArray2664[var13 - '\u8000'];
                  var9[var10] = Class3_Sub13_Sub29.method308(1073741824, var13);
                  var14 = Class38.getItemDefinition(var13, (byte)119).anInt782;
                  if(var14 != 0) {
                     this.anInt2830 = var14;
                  }
               } else {
                  var9[var10] = Class3_Sub13_Sub29.method308(-256 + var13, Integer.MIN_VALUE);
               }
            } else {
               var9[var10] = 0;
            }
         }

         int[] var19 = new int[5];

         for(var11 = 0; var11 < 5; ++var11) {
            var12 = buffer.getByte((byte)-94);
            if(-1 < ~var12 || var12 >= Class15.aShortArrayArray344[var11].length) {
               var12 = 0;
            }

            var19[var11] = var12;
         }

         this.renderAnimationId = buffer.getShort(1);
         long var20 = buffer.getLong(-99);
         this.displayName = Class41.method1052(-29664, var20).method1545((byte)-50);
         this.anInt3960 = buffer.getByte((byte)-41);
         if(var6) {
            this.anInt3974 = buffer.getShort(1);
            this.anInt3965 = this.anInt3960;
            this.anInt3970 = -1;
         } else {
            this.anInt3974 = 0;
            this.anInt3965 = buffer.getByte((byte)-119);
            this.anInt3970 = buffer.getByte((byte)-116);
            if(-256 == ~this.anInt3970) {
               this.anInt3970 = -1;
            }
         }

         var13 = this.anInt3969;
         this.anInt3969 = buffer.getByte((byte)-87);
         if(-1 == ~this.anInt3969) {
            Class162.method2203(this, 8);
         } else {
            int var15 = this.anInt3966;
            int var16 = this.anInt3963;
            int var17 = this.anInt3973;
            var14 = this.anInt3952;
            this.anInt3952 = buffer.getShort(1);
            this.anInt3966 = buffer.getShort(1);
            this.anInt3963 = buffer.getShort(1);
            this.anInt3973 = buffer.getShort(1);
            if(this.anInt3969 != var13 || ~this.anInt3952 != ~var14 || ~this.anInt3966 != ~var15 || var16 != this.anInt3963 || ~this.anInt3973 != ~var17) {
               Node.method518(this, -110);
            }
         }

         if(null == this.aClass52_3962) {
            this.aClass52_3962 = new Class52();
         }

         var14 = this.aClass52_3962.pnpcId;
         this.aClass52_3962.method1161(var19, npcId, ~var4 == -2, 0, var9, this.renderAnimationId);
         if(~var14 != ~npcId) {
            this.anInt2819 = 128 * this.anIntArray2767[0] + this.getSize((byte)114) * 64;
            this.anInt2829 = 128 * this.anIntArray2755[0] - -(64 * this.getSize((byte)114));
         }

         if(this.aClass127_Sub1_2801 != null) {
            this.aClass127_Sub1_2801.method1759();
         }

      } catch (RuntimeException var18) {
         throw Class44.method1067(var18, "e.P(" + var1 + ',' + (buffer != null?"{...}":"null") + ')');
      }
   }

   final void method1868(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, int var11, Class127_Sub1 var12) {
      try {
         if(this.aClass52_3962 != null) {
            Class142 var13 = this.anInt2771 != -1 && 0 == this.anInt2828?client.method45(this.anInt2771, (byte)-20):null;
            Class142 var14 = 0 != ~this.anInt2764 && !this.aBoolean3968 && (~this.anInt2764 != ~this.method1965(false).anInt368 || var13 == null)?client.method45(this.anInt2764, (byte)-20):null;
            Class140_Sub1 var15 = this.aClass52_3962.method1165(this.aClass145Array2809, this.anInt2776, var14, var13, this.anInt2802, this.anInt2793, -120, this.anInt2760, true, this.anInt2832, this.anInt2813);
            int var16 = Class118.method1727((byte)123);
            if(Class138.highDetail && Class3_Sub24_Sub3.anInt3492 < 96 && ~var16 < -51) {
               Class3_Sub1.method90(1);
            }

            int var17;
            if(0 != Class3_Sub13_Sub13.anInt3148 && var16 < 50) {
               for(var17 = 50 - var16; Class56.anInt893 < var17; ++Class56.anInt893) {
                  Class3_Sub6.aByteArrayArray2287[Class56.anInt893] = new byte[102400];
               }

               while(Class56.anInt893 > var17) {
                  --Class56.anInt893;
                  Class3_Sub6.aByteArrayArray2287[Class56.anInt893] = null;
               }
            }

            if(var15 != null) {
               this.anInt2820 = var15.method1871();
               Class140_Sub1 var23;
               if(Class140_Sub6.aBoolean2910 && (-1 == this.aClass52_3962.pnpcId || Node.method522(this.aClass52_3962.pnpcId, 27112).aBoolean1249)) {
                  var23 = Class140_Sub3.method1957(160, this.aBoolean2810, var14 == null?var13:var14, this.anInt2819, 0, this.anInt2829, 0, 1, var15, var1, null != var14?this.anInt2813:this.anInt2832, this.anInt2831, 240, (byte)-49);
                  if(Class138.highDetail) {
                     float var18 = Class138.method1852();
                     float var19 = Class138.method1839();
                     Class138.method1851();
                     Class138.method1825(var18, -150.0F + var19);
                     var23.method1868(0, var2, var3, var4, var5, var6, var7, var8, -1L, var11, (Class127_Sub1)null);
                     Class138.method1830();
                     Class138.method1825(var18, var19);
                  } else {
                     var23.method1868(0, var2, var3, var4, var5, var6, var7, var8, -1L, var11, (Class127_Sub1)null);
                  }
               }

               if(Class102.aClass140_Sub4_Sub1_2141 == this) {
                  for(var17 = RuntimeException_Sub1.aClass96Array2114.length + -1; -1 >= ~var17; --var17) {
                     Class96 var27 = RuntimeException_Sub1.aClass96Array2114[var17];
                     if(var27 != null && ~var27.anInt1355 != 0) {
                        int var21;
                        int var20;
                        if(-2 == ~var27.anInt1360 && 0 <= var27.anInt1359 && ~Class3_Sub13_Sub24.npcs.length < ~var27.anInt1359) {
                           NPC var24 = Class3_Sub13_Sub24.npcs[var27.anInt1359];
                           if(null != var24) {
                              var20 = var24.anInt2819 / 32 - Class102.aClass140_Sub4_Sub1_2141.anInt2819 / 32;
                              var21 = -(Class102.aClass140_Sub4_Sub1_2141.anInt2829 / 32) + var24.anInt2829 / 32;
                              this.method1979((Class127_Sub1)null, var21, var15, var20, var6, var11, 2047, var1, var8, var5, var4, var2, var27.anInt1355, var3, var7);
                           }
                        }

                        if(var27.anInt1360 == 2) {
                           int var29 = 4 * (-Class131.anInt1716 + var27.anInt1356) + 2 + -(Class102.aClass140_Sub4_Sub1_2141.anInt2819 / 32);
                           var20 = 2 + (4 * (var27.anInt1347 - Class82.anInt1152) - Class102.aClass140_Sub4_Sub1_2141.anInt2829 / 32);
                           this.method1979((Class127_Sub1)null, var20, var15, var29, var6, var11, 2047, var1, var8, var5, var4, var2, var27.anInt1355, var3, var7);
                        }

                        if(-11 == ~var27.anInt1360 && var27.anInt1359 >= 0 && ~Class3_Sub13_Sub22.players.length < ~var27.anInt1359) {
                           Player var28 = Class3_Sub13_Sub22.players[var27.anInt1359];
                           if(null != var28) {
                              var20 = -(Class102.aClass140_Sub4_Sub1_2141.anInt2819 / 32) + var28.anInt2819 / 32;
                              var21 = var28.anInt2829 / 32 + -(Class102.aClass140_Sub4_Sub1_2141.anInt2829 / 32);
                              this.method1979((Class127_Sub1)null, var21, var15, var20, var6, var11, 2047, var1, var8, var5, var4, var2, var27.anInt1355, var3, var7);
                           }
                        }
                     }
                  }
               }

               this.method1971(var15, (byte)-103);
               this.method1969((byte)110, var15, var1);
               var23 = null;
               if(!this.aBoolean3968 && 0 != ~this.anInt2842 && this.anInt2805 != -1) {
                  GraphicDefinition var26 = RenderAnimationDefinition.getGraphicDefinition((byte)42, this.anInt2842);
                  var23 = var26.method966(this.anInt2826, (byte)-30, this.anInt2805, this.anInt2761);
                  if(var23 != null) {
                     var23.method1897(0, -this.anInt2799, 0);
                     if(var26.aBoolean536) {
                        if(Class3_Sub13_Sub16.anInt3198 != 0) {
                           var23.method1896(Class3_Sub13_Sub16.anInt3198);
                        }

                        if(0 != Class3_Sub28_Sub9.anInt3623) {
                           var23.method1886(Class3_Sub28_Sub9.anInt3623);
                        }

                        if(Class3_Sub13_Sub9.anInt3111 != 0) {
                           var23.method1897(0, Class3_Sub13_Sub9.anInt3111, 0);
                        }
                     }
                  }
               }

               Class140_Sub1 var25 = null;
               if(!this.aBoolean3968 && this.anObject2796 != null) {
                  if(Class44.anInt719 >= this.anInt2778) {
                     this.anObject2796 = null;
                  }

                  if(~this.anInt2797 >= ~Class44.anInt719 && this.anInt2778 > Class44.anInt719) {
                     if(!(this.anObject2796 instanceof Class140_Sub3)) {
                        var25 = (Class140_Sub1)this.anObject2796;
                     } else {
                        var25 = (Class140_Sub1)((Class140_Sub3)this.anObject2796).method1963(3);
                     }

                     var25.method1897(this.anInt2782 + -this.anInt2819, this.anInt2812 + -this.anInt2831, this.anInt2833 + -this.anInt2829);
                     if(-513 != ~this.anInt2806) {
                        if(~this.anInt2806 != -1025) {
                           if(-1537 == ~this.anInt2806) {
                              var25.method1885();
                           }
                        } else {
                           var25.method1874();
                        }
                     } else {
                        var25.method1900();
                     }
                  }
               }

               if(Class138.highDetail) {
                  var15.aBoolean2699 = true;
                  var15.method1868(var1, var2, var3, var4, var5, var6, var7, var8, var9, var11, this.aClass127_Sub1_2801);
                  if(var23 != null) {
                     var23.aBoolean2699 = true;
                     var23.method1868(var1, var2, var3, var4, var5, var6, var7, var8, var9, var11, this.aClass127_Sub1_2801);
                  }
               } else {
                  if(null != var23) {
                     var15 = ((Class140_Sub1_Sub2)var15).method1943(var23);
                  }

                  if(var25 != null) {
                     var15 = ((Class140_Sub1_Sub2)var15).method1943(var25);
                  }

                  var15.aBoolean2699 = true;
                  var15.method1868(var1, var2, var3, var4, var5, var6, var7, var8, var9, var11, this.aClass127_Sub1_2801);
               }

               if(null != var25) {
                  if(this.anInt2806 == 512) {
                     var25.method1885();
                  } else if(1024 == this.anInt2806) {
                     var25.method1874();
                  } else if(1536 == this.anInt2806) {
                     var25.method1900();
                  }

                  var25.method1897(-this.anInt2782 + this.anInt2819, -this.anInt2812 + this.anInt2831, -this.anInt2833 + this.anInt2829);
               }

            }
         }
      } catch (RuntimeException var22) {
         throw Class44.method1067(var22, "e.IA(" + var1 + ',' + var2 + ',' + var3 + ',' + var4 + ',' + var5 + ',' + var6 + ',' + var7 + ',' + var8 + ',' + var9 + ',' + var11 + ',' + (var12 != null?"{...}":"null") + ')');
      }
   }

   private final void method1979(Class127_Sub1 var1, int var2, Class140_Sub1 var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15) {
      try {
         int var16 = var4 * var4 - -(var2 * var2);
         if(-17 >= ~var16 && -360001 <= ~var16) {
            int var17 = (int)(325.949D * Math.atan2((double)var4, (double)var2)) & var7;
            Class140_Sub1 var18 = Class128.method1763(true, var17, this.anInt2829, var13, this.anInt2819, var3, this.anInt2831);
            if(var18 != null) {
               if(Class138.highDetail) {
                  float var19 = Class138.method1852();
                  float var20 = Class138.method1839();
                  Class138.method1851();
                  Class138.method1825(var19, var20 - 150.0F);
                  var18.method1868(0, var12, var14, var11, var10, var5, var15, var9, -1L, var6, var1);
                  Class138.method1830();
                  Class138.method1825(var19, var20);
               } else {
                  var18.method1868(0, var12, var14, var11, var10, var5, var15, var9, -1L, var6, var1);
               }
            }

         }
      } catch (RuntimeException var21) {
         throw Class44.method1067(var21, "e.N(" + (var1 != null?"{...}":"null") + ',' + var2 + ',' + (var3 != null?"{...}":"null") + ',' + var4 + ',' + var5 + ',' + var6 + ',' + var7 + ',' + var8 + ',' + var9 + ',' + var10 + ',' + var11 + ',' + var12 + ',' + var13 + ',' + var14 + ',' + var15 + ')');
      }
   }

   final boolean method1966(byte var1) {
      try {
         if(var1 != 17) {
            anIntArray3954 = (int[])null;
         }

         return this.aClass52_3962 != null;
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "e.L(" + var1 + ')');
      }
   }

   final RSString method1980(int var1) {
      try {
         RSString var2 = this.displayName;
         if(var1 != 0) {
            this.method1868(-63, 126, 58, -9, -74, -119, -45, -114, -62L, -76, (Class127_Sub1)null);
         }

         if(Class3_Sub30_Sub1.aClass94Array3802 != null) {
            var2 = RenderAnimationDefinition.method903(new RSString[]{Class3_Sub30_Sub1.aClass94Array3802[this.anInt3958], var2}, (byte)-92);
         }

         if(null != OutputStream_Sub1.aClass94Array45) {
            var2 = RenderAnimationDefinition.method903(new RSString[]{var2, OutputStream_Sub1.aClass94Array45[this.anInt3958]}, (byte)-128);
         }

         return var2;
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "e.Q(" + var1 + ')');
      }
   }

   final void method1867(int var1, int var2, int var3, int var4, int var5) {}

   final void method1981(byte var1, int var2, boolean var3, int var4) {
      try {
         super.method1967(var1 + -128, this.getSize((byte)114), var2, var4, var3);
         if(var1 != 126) {
            aClass94_3964 = (RSString)null;
         }

      } catch (RuntimeException var6) {
         throw Class44.method1067(var6, "e.O(" + var1 + ',' + var2 + ',' + var3 + ',' + var4 + ')');
      }
   }

   protected final void finalize() {}

   public static void method1982(byte var0) {
      try {
         aClass94_3971 = null;
         aClass94_3957 = null;
         anIntArray3951 = null;
         aClass94_3961 = null;
         anIntArray3959 = null;
         if(var0 <= 116) {
            method1982((byte)-48);
         }

         anIntArray3954 = null;
         aClass94_3964 = null;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "e.R(" + var0 + ')');
      }
   }

   final int method1871() {
      try {
         return this.anInt2820;
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "e.MA()");
      }
   }

}
