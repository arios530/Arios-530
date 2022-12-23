import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

final class Class146_Sub1 extends Class146 implements MouseWheelListener {

   static boolean isMoved = false;
   private int anInt2941 = 0;
   
   static int moveAmt = 0;
   
   


   final void method2082(boolean var1, Component var2) {
      if(var1) {
         this.mouseWheelMoved((MouseWheelEvent)null);
      }

      var2.removeMouseWheelListener(this);
   }

   final synchronized int method2078(int var1) {
      int var2 = this.anInt2941;
      this.anInt2941 = 0;
      if(var1 != -1) {
         this.anInt2941 = -53;
      }

      return var2;
   }

   public final synchronized void mouseWheelMoved(MouseWheelEvent var1) {
      this.anInt2941 += var1.getWheelRotation();
      moveAmt = var1.getUnitsToScroll() * 2;
      isMoved = true;
      if (ObjectDefinition.aBooleanArray1490[81] && var1.getWheelRotation() == 1) {// Out
		    if (client.anInt1855 <= 1200) { 
		        client.anInt1855 += 300; 
                //System.out.println("shift out " + Thread.currentThread().getName());
                Class73.method1308(Class3_Sub4.createRSString("::viewzoom"), false);
		    } else {
		        return;
		    }
		   }
		   if (ObjectDefinition.aBooleanArray1490[81] && var1.getWheelRotation() == -1){// In
			if (client.anInt1855 >= 300) { 
			    client.anInt1855 -= 300; 
                //System.out.println("shift in " + Thread.currentThread().getName());
                Class73.method1308(Class3_Sub4.createRSString("::viewzoom"), false);
		    } else {
		        return;
		    }
      }
   }
   
   final void method2084(Component var1, int var2) {
      if(var2 < -70) {
         var1.addMouseWheelListener(this);
      }
   }

}
