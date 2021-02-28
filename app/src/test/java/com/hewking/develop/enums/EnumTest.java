package com.hewking.develop.enums;

import org.junit.Test;

import java.math.BigInteger;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class EnumTest{

    enum Week{

        Monday,ThusDay;

        // Implementing a fromString method on an enum type
        private static final Map<String, Week> stringToEnum =
                Stream.of(values()).collect(
                        toMap(Object::toString, e -> e));

        // Returns Operation for string, if any
        public static Optional<Week> fromString(String symbol) {
            return Optional.ofNullable(stringToEnum.get(symbol));
        }

        static <K> String string(K k){
            return k.toString();
        }



    }

    @Test
    public void testStreamPrime(){
        // 计算梅森素数
        primes().map(p -> {
            return TWO.pow(p.intValue()).subtract(BigInteger.ONE);
        }).filter(p -> p.isProbablePrime(50))
                .limit(20)
                .forEach(System.out::println);
    }

    public static Stream<BigInteger> primes(){
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }

    public static final BigInteger TWO = BigInteger.valueOf(2L);

}