
final class Class98 {

   boolean aBoolean1382 = false;
   int anInt1383 = -1;
   Class3_Sub17 aClass3_Sub17_1384 = null;
   short[] aShortArray1385;
   boolean aBoolean1386 = false;
   short[] aShortArray1387;
   short[] aShortArray1388;
   private static byte[] aByteArray1389 = new byte[500];
   private static short[] aShortArray1390 = new short[500];
   private static short[] aShortArray1391 = new short[500];
   private static short[] aShortArray1392 = new short[500];
   byte[] aByteArray1393;
   private static short[] aShortArray1394 = new short[500];
   short[] aShortArray1395;
   short[] aShortArray1396;
   private static short[] aShortArray1397 = new short[500];


   public static void method1595() {
      aShortArray1397 = null;
      aShortArray1394 = null;
      aShortArray1391 = null;
      aShortArray1390 = null;
      aShortArray1392 = null;
      aByteArray1389 = null;
   }

   Class98(byte[] var1, Class3_Sub17 var2) {
      this.aClass3_Sub17_1384 = var2;
      RSByteBuffer var3 = new RSByteBuffer(var1);
      RSByteBuffer var4 = new RSByteBuffer(var1);
      var3.index = 2;
      int var5 = var3.getByte((byte)-86);
      int var6 = 0;
      int var7 = -1;
      int var8 = -1;
      var4.index = var3.index + var5;

      int var9;
      for(var9 = 0; var9 < var5; ++var9) {
         int var10 = this.aClass3_Sub17_1384.anIntArray2466[var9];
         if(var10 == 0) {
            var7 = var9;
         }

         int var11 = var3.getByte((byte)-55);
         if(var11 > 0) {
            if(var10 == 0) {
               var8 = var9;
            }

            aShortArray1397[var6] = (short)var9;
            short var12 = 0;
            if(var10 == 3) {
               var12 = 128;
            }

            if((var11 & 1) != 0) {
               aShortArray1394[var6] = (short)var4.method797(-21208);
            } else {
               aShortArray1394[var6] = var12;
            }

            if((var11 & 2) != 0) {
               aShortArray1391[var6] = (short)var4.method797(-21208);
            } else {
               aShortArray1391[var6] = var12;
            }

            if((var11 & 4) != 0) {
               aShortArray1390[var6] = (short)var4.method797(-21208);
            } else {
               aShortArray1390[var6] = var12;
            }

            aByteArray1389[var6] = (byte)(var11 >>> 3 & 3);
            if(var10 == 2) {
               aShortArray1394[var6] = (short)(((aShortArray1394[var6] & 255) << 3) + (aShortArray1394[var6] >> 8 & 7));
               aShortArray1391[var6] = (short)(((aShortArray1391[var6] & 255) << 3) + (aShortArray1391[var6] >> 8 & 7));
               aShortArray1390[var6] = (short)(((aShortArray1390[var6] & 255) << 3) + (aShortArray1390[var6] >> 8 & 7));
            }

            aShortArray1392[var6] = -1;
            if(var10 != 1 && var10 != 2 && var10 != 3) {
               if(var10 == 5) {
                  this.aBoolean1386 = true;
               } else if(var10 == 7) {
                  this.aBoolean1382 = true;
               }
            } else if(var7 > var8) {
               aShortArray1392[var6] = (short)var7;
               var8 = var7;
            }

            ++var6;
         }
      }

      if(var4.index != var1.length) {
         throw new RuntimeException();
      } else {
         this.anInt1383 = var6;
         this.aShortArray1385 = new short[var6];
         this.aShortArray1388 = new short[var6];
         this.aShortArray1396 = new short[var6];
         this.aShortArray1395 = new short[var6];
         this.aShortArray1387 = new short[var6];
         this.aByteArray1393 = new byte[var6];

         for(var9 = 0; var9 < var6; ++var9) {
            this.aShortArray1385[var9] = aShortArray1397[var9];
            this.aShortArray1388[var9] = aShortArray1394[var9];
            this.aShortArray1396[var9] = aShortArray1391[var9];
            this.aShortArray1395[var9] = aShortArray1390[var9];
            this.aShortArray1387[var9] = aShortArray1392[var9];
            this.aByteArray1393[var9] = aByteArray1389[var9];
         }

      }
   }

}
