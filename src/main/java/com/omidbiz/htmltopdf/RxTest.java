package com.omidbiz.htmltopdf;

import rx.Observable;
import rx.functions.Action1;

public class RxTest
{
    
    public static void main(String[] args)
    {
        hello("omid", "test");
    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }

        });
    }
    
}
