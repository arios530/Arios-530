import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

final class Class148 implements KeyListener, FocusListener {

   static boolean aBoolean1905 = true;
   static int anInt1906;
   static RSString aClass94_1907 = Class3_Sub4.createRSString(" autres options", (byte)-120);
   static int anInt1908 = 0;
   static int anInt1909;
   static int anInt1910;
   static Class93 aClass93_1911 = new Class93(260);
   static int anInt1912;
   static RSString aClass94_1913 = Class3_Sub4.createRSString("scrollbar", (byte)-127);
   static int anInt1914;
   static RSString aClass94_1915 = Class3_Sub4.createRSString("Null", (byte)-126);
   static CacheIndex aClass153_1916;
   static RSString aClass94_1917 = Class3_Sub4.createRSString(" <col=00ff80>", (byte)-117);
   static int anInt1918 = 0;


   public final synchronized void keyPressed(KeyEvent var1) {
      try {
         if(null != Class3_Sub13_Sub3.aClass148_3049) {
            Class3_Sub13_Sub33.anInt3398 = 0;
            int var2 = var1.getKeyCode();
            if(0 <= var2 && Class117.anIntArray1611.length > var2) {
               var2 = Class117.anIntArray1611[var2];
               if(0 != (var2 & 128)) {
                  var2 = -1;
               }
            } else {
               var2 = -1;
            }
            
             /**
              * Tab to reply
              */
             if (var1.getKeyCode() == KeyEvent.VK_TAB) {
                 Class73.method1308(Class3_Sub4.createRSString("::reply"), false);
             }

             /**
              * Close interface using escape.
              */
             if (var1.getKeyCode() == KeyEvent.VK_ESCAPE) {
                 Class73.method1308(Class3_Sub4.createRSString("::xinter"), false);
             }

            /**
             * Controls arrow key mouse movement
             */
            if(-1 >= ~Class3_Sub13.anInt2384 && -1 >= ~var2) {
               Class151_Sub1.anIntArray2952[Class3_Sub13.anInt2384] = var2;
               Class3_Sub13.anInt2384 = 127 & Class3_Sub13.anInt2384 - -1;
               if(Class3_Sub13.anInt2384 == Class133.anInt1744) {
                  Class3_Sub13.anInt2384 = -1;
               }
            }

            int var3;
            if(~var2 <= -1) {
               var3 = 127 & 1 + Class25.anInt491;
               if(var3 != Class3_Sub28_Sub9.anInt3620) {
                  Class129.anIntArray1693[Class25.anInt491] = var2;
                  Class155.anIntArray1978[Class25.anInt491] = -1;
                  Class25.anInt491 = var3;
               }
            }

            var3 = var1.getModifiers();
            if(-1 != ~(var3 & 10) || 85 == var2 || -11 == ~var2) {
               var1.consume();
            }
         }

      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "uf.keyPressed(" + (var1 != null?"{...}":"null") + ')');
      }
   }

   public final void keyTyped(KeyEvent var1) {
      try {
         if(Class3_Sub13_Sub3.aClass148_3049 != null) {
            int var2 = Class79.method1386(true, var1);
            if(-1 >= ~var2) {
               int var3 = 1 + Class25.anInt491 & 127;
               if(~Class3_Sub28_Sub9.anInt3620 != ~var3) {
                  Class129.anIntArray1693[Class25.anInt491] = -1;
                  Class155.anIntArray1978[Class25.anInt491] = var2;
                  Class25.anInt491 = var3;
               }
            }
         }

         var1.consume();
      } catch (RuntimeException var4) {
         throw Class44.method1067(var4, "uf.keyTyped(" + (var1 != null?"{...}":"null") + ')');
      }
   }

   public final synchronized void focusLost(FocusEvent var1) {
      try {
         if(null != Class3_Sub13_Sub3.aClass148_3049) {
            Class3_Sub13.anInt2384 = -1;
         }

      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "uf.focusLost(" + (var1 != null?"{...}":"null") + ')');
      }
   }

   public final synchronized void keyReleased(KeyEvent var1) {
      try {
         if(null != Class3_Sub13_Sub3.aClass148_3049) {
            Class3_Sub13_Sub33.anInt3398 = 0;
            int var2 = var1.getKeyCode();
            if(var2 >= 0 && ~Class117.anIntArray1611.length < ~var2) {
               var2 = Class117.anIntArray1611[var2] & -129;
            } else {
               var2 = -1;
            }
            if (Class130.chatboxOpen && var1.getKeyCode() == KeyEvent.VK_SPACE) {
                 int offset = 0;
                 if (Class101.aBoolean1419 && ObjectDefinition.aBooleanArray1490[81] && ~Class3_Sub13_Sub34.anInt3415 < -3) {
                     offset = Class3_Sub13_Sub34.anInt3415 + -2;
                 } else {
                     offset = Class3_Sub13_Sub34.anInt3415 + -1;
                 }
                 Class2.method78(Class117.anIntArray1613[offset], false, ((Class27.anIntArray512[offset] >> 16) << 16));
                 return;
             }
            if (Class130.chatboxOpen && (var1.getKeyCode() >= 0x30 && var1.getKeyCode() <= 0x39)) {
                 int offset = 0;
                 if (Class101.aBoolean1419 && ObjectDefinition.aBooleanArray1490[81] && ~Class3_Sub13_Sub34.anInt3415 < -3) {
                     offset = Class3_Sub13_Sub34.anInt3415 + -2;
                 } else {
                     offset = Class3_Sub13_Sub34.anInt3415 + -1;
                 }
                 int hash = (((Class27.anIntArray512[offset] >> 16) << 16) | (var1.getKeyCode() - 47));
                 Class2.method78(Class117.anIntArray1613[offset], false, hash);
                 return;
            }
            if(Class3_Sub13.anInt2384 >= 0 && ~var2 <= -1) {
               Class151_Sub1.anIntArray2952[Class3_Sub13.anInt2384] = ~var2;
               Class3_Sub13.anInt2384 = 127 & 1 + Class3_Sub13.anInt2384;
               if(~Class133.anInt1744 == ~Class3_Sub13.anInt2384) {
                  Class3_Sub13.anInt2384 = -1;
               }
            }
         }

         var1.consume();
      } catch (RuntimeException var3) {
         throw Class44.method1067(var3, "uf.keyReleased(" + (var1 != null?"{...}":"null") + ')');
      }
   }

   public final void focusGained(FocusEvent var1) {}

   public static void method2085(int var0) {
      try {
         aClass93_1911 = null;
         aClass153_1916 = null;
         if(var0 > 81) {
            aClass94_1915 = null;
            aClass94_1913 = null;
            aClass94_1917 = null;
            aClass94_1907 = null;
         }
      } catch (RuntimeException var2) {
         throw Class44.method1067(var2, "uf.A(" + var0 + ')');
      }
   }

   static final void method2086(byte var0) {
      try {
         if(var0 >= 62) {
            int var1 = Class102.aClass140_Sub4_Sub1_2141.anInt2819 + Class3_Sub13_Sub18.anInt3216;
            int var2 = Class102.aClass140_Sub4_Sub1_2141.anInt2829 - -InputStream_Sub1.anInt42;
            if(499 < ~(-var1 + Class3_Sub13_Sub13.anInt3155) || ~(-var1 + Class3_Sub13_Sub13.anInt3155) < -501 || ~(Class62.anInt942 + -var2) > 499 || -501 > ~(-var2 + Class62.anInt942)) {
               Class3_Sub13_Sub13.anInt3155 = var1;
               Class62.anInt942 = var2;
            }

            if(~Class62.anInt942 != ~var2) {
               Class62.anInt942 += (-Class62.anInt942 + var2) / 16;
            }

            if(~Class3_Sub13_Sub13.anInt3155 != ~var1) {
               Class3_Sub13_Sub13.anInt3155 += (-Class3_Sub13_Sub13.anInt3155 + var1) / 16;
            }

            if(Class15.aBoolean346) {
               for(int var3 = 0; ~Class3_Sub23.anInt2537 < ~var3; ++var3) {
                  int var4 = Class133.anIntArray1755[var3];
                  if(98 != var4) {
                     if(-100 != ~var4) {
                        if(var4 != 96) {
                           if(var4 == 97) {
                              GraphicDefinition.anInt531 = GraphicDefinition.anInt531 + 191 & -128;
                           }
                        } else {
                           GraphicDefinition.anInt531 = GraphicDefinition.anInt531 - 65 & -128;
                        }
                     } else {
                        Class3_Sub9.anInt2309 = -16 & Class3_Sub9.anInt2309 - 17;
                     }
                  } else {
                     Class3_Sub9.anInt2309 = -16 & Class3_Sub9.anInt2309 + 47;
                  }
               }
            } else {
               if(ObjectDefinition.aBooleanArray1490[98]) {
                  Class27.anInt517 += (-Class27.anInt517 + 12) / 2;
               } else if(!ObjectDefinition.aBooleanArray1490[99]) {
                  Class27.anInt517 /= 2;
               } else {
                  Class27.anInt517 += (-Class27.anInt517 + -12) / 2;
               }

               if(!ObjectDefinition.aBooleanArray1490[96]) {
                  if(ObjectDefinition.aBooleanArray1490[97]) {
                     Class3_Sub5.anInt2281 += (-Class3_Sub5.anInt2281 + 24) / 2;
                  } else {
                     Class3_Sub5.anInt2281 /= 2;
                  }
               } else {
                  Class3_Sub5.anInt2281 += (-Class3_Sub5.anInt2281 + -24) / 2;
               }

               Class3_Sub9.anInt2309 += Class27.anInt517 / 2;
               GraphicDefinition.anInt531 += Class3_Sub5.anInt2281 / 2;
            }

            Class47.method1098((byte)-94);
         }
      } catch (RuntimeException var5) {
         throw Class44.method1067(var5, "uf.B(" + var0 + ')');
      }
   }

}
