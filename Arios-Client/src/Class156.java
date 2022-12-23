import com.jogamp.opengl.GL2;

import java.nio.ByteBuffer;
import com.jogamp.opengl.GL2;
//import javax.media.opengl.GL;
final class Class156 {

   private int anInt1991;
   private int anInt1992;
   private int anInt1993;
   private boolean aBoolean1994;


   final void method2168(ByteBuffer var1) {
      if(var1.limit() <= this.anInt1993) {
         GL2 var2 = Class138.aGL1804;
         var2.glBindBuffer('\u8892', this.anInt1991);
         var2.glBufferSubData('\u8892', 0, var1.limit(), var1);
      } else {
         this.method2172(var1);
      }

   }

   protected final void finalize() throws Throwable {
      if(this.anInt1991 != -1) {
         Class31.method989(this.anInt1991, this.anInt1993, this.anInt1992);
         this.anInt1991 = -1;
         this.anInt1993 = 0;
      }

      super.finalize();
   }

   final void method2169() {
      GL2 var1 = Class138.aGL1804;
      var1.glBindBuffer('\u8892', this.anInt1991);
   }

   public Class156() {
      this(false);
   }

   final void method2170(ByteBuffer var1) {
      GL2 var2 = Class138.aGL1804;
      var2.glBindBuffer('\u8893', this.anInt1991);
      var2.glBufferData('\u8893', var1.limit(), var1, this.aBoolean1994?'\u88e0':'\u88e4');
      Class31.anInt585 += var1.limit() - this.anInt1993;
      this.anInt1993 = var1.limit();
   }

   final void method2171() {
      GL2 var1 = Class138.aGL1804;
      var1.glBindBuffer('\u8893', this.anInt1991);
   }

   final void method2172(ByteBuffer var1) {
      GL2 var2 = Class138.aGL1804;
      var2.glBindBuffer('\u8892', this.anInt1991);
      var2.glBufferData('\u8892', var1.limit(), var1, this.aBoolean1994?'\u88e0':'\u88e4');
      Class31.anInt585 += var1.limit() - this.anInt1993;
      this.anInt1993 = var1.limit();
   }

   Class156(boolean var1) {
      this.anInt1991 = -1;
      this.anInt1993 = 0;
      GL2 var2 = Class138.aGL1804;
      int[] var3 = new int[1];
      var2.glGenBuffers(1, var3, 0);
      this.aBoolean1994 = var1;
      this.anInt1991 = var3[0];
      this.anInt1992 = Class31.anInt582;
   }
}
