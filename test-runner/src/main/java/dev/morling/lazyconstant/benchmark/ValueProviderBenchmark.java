package dev.morling.lazyconstant.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import dev.morling.lazyconstant.ValueProviderConstant;
import dev.morling.lazyconstant.ValueProviderEagerFinal;
import dev.morling.lazyconstant.ValueProviderLazy;
import dev.morling.lazyconstant.ValueProviderLazyConstant;
import dev.morling.lazyconstant.ValueProviderLazyHolder;

public class ValueProviderBenchmark {

//    @Benchmark
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 3)
//    @BenchmarkMode(Mode.Throughput)
//    public void constant(ValueProviderHolder vph, Blackhole blackHole) {
//        blackHole.consume(vph.valueProviderConstant.getValue());
//    }

//    @Benchmark
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 3)
//    @BenchmarkMode(Mode.Throughput)
//    public void eagerFinal(ValueProviderHolder vph, Blackhole blackHole) {
//        blackHole.consume(vph.valueProviderEagerFinal.getValue());
//    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    public void lazyHolder(ValueProviderHolder vph, Blackhole blackHole) {
        blackHole.consume(vph.valueProviderLazyHolder.getValue());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    public void lazy(ValueProviderHolder vph, Blackhole blackHole) {
        blackHole.consume(vph.valueProviderLazy.getValue());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2)
    @Measurement(iterations = 3)
    @BenchmarkMode(Mode.Throughput)
    public void constantDynamic(ValueProviderHolder vph, Blackhole blackHole) {
        blackHole.consume(vph.valueProviderLazyConstant.getValue());
    }

    @State(Scope.Benchmark)
    public static class ValueProviderHolder {

        private ValueProviderConstant valueProviderConstant;
        private ValueProviderEagerFinal valueProviderEagerFinal;
        private ValueProviderLazy valueProviderLazy;
        private ValueProviderLazyHolder valueProviderLazyHolder;
        private ValueProviderLazyConstant valueProviderLazyConstant;

        @Setup(Level.Iteration)
        public void setup() {
            this.valueProviderConstant = new ValueProviderConstant();
            this.valueProviderEagerFinal = new ValueProviderEagerFinal();
            this.valueProviderLazy = new ValueProviderLazy();
            this.valueProviderLazyHolder = new ValueProviderLazyHolder();
            this.valueProviderLazyConstant = new ValueProviderLazyConstant();
        }
    }
}
