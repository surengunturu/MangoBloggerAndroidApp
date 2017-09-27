package com.mangoblogger.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;

/*
*  As you can see this is Animation class you can try any of this animation.
* */
public class AnimationUtils {


    public static void animate(RecyclerView.ViewHolder holder, boolean scrollDown){


        AnimatorSet set=new AnimatorSet();


      //  ObjectAnimator  animator0=ObjectAnimator.ofFloat(holder.itemView,"scaleX",0.5f,0.8f,1.0f);
       // ObjectAnimator  animator3=ObjectAnimator.ofFloat(holder.itemView,"scaleY",0.5f,0.8f,1.0f);
     //  ObjectAnimator  animator=ObjectAnimator.ofFloat(holder.itemView,"translationY",scrollDown == true?100:-100,0);
    //  ObjectAnimator  animator1=ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,20,20,-5,5,0,0);
   //    ObjectAnimator  animator=ObjectAnimator.ofFloat(holder.itemView,"translationX",100,0);
    //   ObjectAnimator  animator1=ObjectAnimator.ofFloat(holder.itemView,"rotation",45,0);
       // set.setInterpolator(new AnticipateInterpolator());



        /* Scaling */
        ObjectAnimator  animator1= ObjectAnimator.ofFloat(holder.itemView,"translationY",scrollDown == true?200:-200,0);
        ObjectAnimator  animator2=ObjectAnimator.ofFloat(holder.itemView,"translationX",scrollDown == true? 300: 300,0);
        ObjectAnimator  animator3=ObjectAnimator.ofFloat(holder.itemView,"scaleX",0.5f,0.8f,1.0f);
        ObjectAnimator  animator4=ObjectAnimator.ofFloat(holder.itemView,"scaleY",0.5f,0.8f,1.0f);





        /* fading*/
        // ObjectAnimator  animator4=ObjectAnimator.ofFloat(holder.itemView,"alpha",0f,0.5f);



        /* Shaking effect*/

    /*     ObjectAnimator    animator1=ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,20,20,-5,5,0,0);
           ObjectAnimator    animator= ObjectAnimator.ofFloat(holder.itemView,"translationY",scrollDown == true?100:-100,0); */


        /* shaking With scaling */

     /*    ObjectAnimator    animator1=ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,20,20,-5,5,0,0);
           ObjectAnimator    animator=ObjectAnimator.ofFloat(holder.itemView,"translationY",scrollDown == true?100:-100,0);
           ObjectAnimator    animator0=ObjectAnimator.ofFloat(holder.itemView,"scaleX",0.5f,0.8f,1.0f);
           ObjectAnimator    animator3=ObjectAnimator.ofFloat(holder.itemView,"scaleY",0.5f,0.8f,1.0f); */


        // rotation with translation

     /*    ObjectAnimator  animator=ObjectAnimator.ofFloat(holder.itemView,"translationX",100,0);
          ObjectAnimator  animator1=ObjectAnimator.ofFloat(holder.itemView,"rotation",45,0);  */


        /*Translation in axixs */
     //   ObjectAnimator  animator=ObjectAnimator.ofFloat(holder.itemView,"translationX",scrollDown == true? 100: -100,0);



        /*flip animation

        ObjectAnimator  animator2=ObjectAnimator.ofFloat(holder.itemView,"alpha",1.0f,0.0f);
        ObjectAnimator  animator3=ObjectAnimator.ofFloat(holder.itemView,"rotationY",90,0);
        ObjectAnimator  animator4=ObjectAnimator.ofFloat(holder.itemView,"alpha",0.0f,1.0f);  */



        set.setDuration(800);
        set.setInterpolator(new OvershootInterpolator());
        set.playTogether(animator1,animator2,animator3,animator4);
        set.start();




    }

}
