package com.kamal.marcus.rxjavaworkaround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cold observable example
//        coldObservableExample();

        //Convert cold observable to Hot Observable
//        convertColdToHotObservable();

        //Subjects
        //Publish Subject
//        publishSubjectExample();

        //Behavioural Subject
//        behaviouralSubjectExample();

        //Replay Subject
//        replaySubjectExample();

        //Async Subject
//        asyncSubjectExample();


        //Schedulers & Upstream and Downstream
//        schedularsExample();


        //Operators
        //Create Operator
//        createOperatorExample();

        //fromArray Operator
//        fromArrayOperatorExample();

        //Different Operators
//        diffirentOperatorsExample();
    }

    private void diffirentOperatorsExample() {
        Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext("Android");
            emitter.onNext(3);
            emitter.onNext(4);

            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).
                doOnNext(i -> System.out.println("current item before mapping " + i))
                .observeOn(AndroidSchedulers.mainThread())
//                .map(o->Integer.parseInt(o.toString())*2)
//                .filter(i -> !i.toString().equals("Android"))
                .subscribe(observer -> System.out.println("current item result " + observer));
    }

    private void fromArrayOperatorExample() {
        List<Integer> itemsList = new ArrayList<>();
        itemsList.add(0);
        itemsList.add(1);
        itemsList.add(2);
        itemsList.add(3);
        itemsList.add(4);
        Observable observable = Observable.fromArray(itemsList.toArray());
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("current item " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("current item Error " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("current item onComplete ");
            }
        };
        observable.subscribe(observer);
    }

    private void createOperatorExample() {
        Observable observable = Observable.create(emitter -> {
            for (int i = 0; i < 5; i++) {
                emitter.onNext(i);
            }
            emitter.onComplete();
        });
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("current item " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("current item Error " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("current item onComplete ");
            }
        };
        observable.subscribe(observer);
    }

    private void schedularsExample() {
        Observable.just(0, "text", 2, new ArrayList<>(), 4, 5, 6, 7, 8, 9)
                .subscribeOn(Schedulers.computation())
                .doOnNext(i -> System.out.println("First observer upstream current thread " + i + " " + Thread.currentThread().getName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> System.out.println("First observer downstream current thread " + o + " " + Thread.currentThread().getName()));
    }

    private void asyncSubjectExample() {
        AsyncSubject<Integer> subject = AsyncSubject.create();
        subject.subscribe(i -> System.out.println("First Observer " + i));
        subject.onNext(0);
        wait(1000);
        subject.onNext(1);
        wait(1000);
        subject.onNext(2);
        wait(1000);
        subject.onNext(3);
        wait(1000);
        subject.onNext(4);
        wait(1000);
        subject.subscribe(i -> System.out.println("Second Observer " + i));
        subject.onNext(5);
        wait(1000);
        subject.onNext(6);
        wait(1000);
        subject.onNext(7);
    }

    private void replaySubjectExample() {
        ReplaySubject<Integer> subject = ReplaySubject.create();
        subject.subscribe(i -> System.out.println("First Observer " + i));
        subject.onNext(0);
        wait(1000);
        subject.onNext(1);
        wait(1000);
        subject.onNext(2);
        wait(1000);
        subject.onNext(3);
        wait(1000);
        subject.onNext(4);
        wait(1000);
        subject.subscribe(i -> System.out.println("Second Observer " + i));
        subject.onNext(5);
        wait(1000);
        subject.onNext(6);
        wait(1000);
        subject.onNext(7);
    }

    private void behaviouralSubjectExample() {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        subject.subscribe(i -> System.out.println("First Observer " + i));
        subject.onNext(0);
        wait(1000);
        subject.onNext(1);
        wait(1000);
        subject.onNext(2);
        wait(1000);
        subject.onNext(3);
        wait(1000);
        subject.onNext(4);
        wait(1000);
        wait(4000);
        subject.subscribe(i -> System.out.println("Second Observer " + i));
        subject.onNext(5);
        wait(1000);
        subject.onNext(6);
        wait(1000);
        subject.onNext(7);
    }

    private void publishSubjectExample() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(i -> System.out.println("First Observer " + i));
        subject.onNext(0);
        wait(1000);
        subject.onNext(1);
        wait(1000);
        subject.onNext(2);
        wait(1000);
        subject.onNext(3);
        wait(1000);
        subject.onNext(4);
        wait(1000);
        wait(4000);
        subject.subscribe(i -> System.out.println("Second Observer " + i));
        subject.onNext(5);
        wait(1000);
        subject.onNext(6);
        wait(1000);
        subject.onNext(7);
    }

    private void convertColdToHotObservable() {
        ConnectableObservable<Long> observable = ConnectableObservable.intervalRange(0, 5, 0, 2, TimeUnit.SECONDS).publish();
        observable.connect();
        observable.subscribe(o -> System.out.println("First observer " + o));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(o -> System.out.println("Second observer  " + o));
    }

    private void coldObservableExample() {
        Observable<Long> observable = Observable.intervalRange(0, 5, 0, 2, TimeUnit.SECONDS);
        observable.subscribe(o -> System.out.println("First observer " + o));

        observable.subscribe(o -> System.out.println("Second observer  " + o));
    }

    private void wait(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
