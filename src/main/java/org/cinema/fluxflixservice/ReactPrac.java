package org.cinema.fluxflixservice;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class ReactPrac {

    public static void main(String[] args) {
        System.out.println("Testing");
//        fluxes();
//        subscribeOptions();
        backPressure();
    }

    private static void fluxes() {
        Flux<String> stringFlux = Flux
                .just("Rasik", "Rohit", "Rutvij", "Nitish", "Sushant", "Aman", "Parag", "Abinav");

        //map
        Flux<Integer> map = stringFlux.map(s -> s.length());
        map.subscribe(System.out::println);

        //flatMap

        Flux<String> stringFlux1 = stringFlux.flatMap(s -> Flux.just(s));
        stringFlux1.subscribe(System.out::println);

/*
        Flux<Flux<String>> map1 = stringFlux.map(s -> Flux.just(s));
        map1.subscribe(fx -> {
            fx.subscribe(System.out::print);
        });


        Flux<Mono<String>> monos = stringFlux.map(s -> Mono.just(s));
        monos.subscribe(fx -> {
            fx.subscribe(System.out::print);
        });*/

        // List
        Mono<List<String>> listMono = stringFlux.collectList();
        listMono.subscribe(System.out::println);

        //MOno<List> to Flux
        Flux<String> stringFlux2 = stringFlux.collectList().flatMapMany(lst -> Flux.fromIterable(lst));
        stringFlux2.subscribe(System.out::println);

        //Map
        Mono<Map<String, String>> monoMap = stringFlux.collectMap(
                item -> item.substring(0, 3),
                item -> item
        );
        monoMap.subscribe(System.out::println);
    }


    static void subscribeOptions() {
        Flux<Integer> flux = Flux.range(1, 50);

        flux.subscribe(System.out::print);

        System.out.println("\n==============================================");
        //onError
        flux.subscribe(i -> System.out.print(" " + i * 2),
                error -> System.out.println("ERROR: " + error));

        System.out.println("\n==============================================");
        //onError , OnComplete
        flux.subscribe(i -> System.out.print(" " + i * 2),
                error -> System.out.println("ERROR: " + error),
                () -> System.out.println(" Completed"));

        // on Exception
        Flux<Object> exceeds_limits = Flux.range(1, 10)
                .map(i -> {
                    int i1 = i * i;

                    if (i1 > 50)
                        throw new RuntimeException("Exceeds Limits");
                    return i1;
                });
        exceeds_limits.subscribe(i -> System.out.print(" " + i),
                error -> System.out.println("ERROR: " + error),
                () -> System.out.println("DONE"));
    }

    static void backPressure() {
        Flux<Integer> ints = Flux.range(1, 100).doOnRequest(
                r -> System.out.println("Elements requested = " + r));

        ints.subscribe(new BaseSubscriber<Integer>() {

            @Override
            public void hookOnSubscribe(Subscription subscription) {
                request(1); // how many at time of subscription
            }

            @Override
            public void hookOnNext(Integer integer) {
                System.out.println("calling hookOnNext = " + integer);
                request(1);
            }

        });


        ints.subscribe(new BaseSubscriber<Integer>() {

            @Override
            public void hookOnSubscribe(Subscription subscription) {
                request(100); // how many at time of subscription
            }

            @Override
            public void hookOnNext(Integer integer) {
                System.out.println("calling hookOnNext = " + integer);
                cancel(); // cancelling after 1st element, even though we requested 100
            }

        });


    }
}
