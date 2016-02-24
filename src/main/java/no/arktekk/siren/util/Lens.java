package no.arktekk.siren.util;


import java.util.function.BiFunction;
import java.util.function.Function;

public final class Lens<A, B> implements Function<A, B> {

    public final Function<A, B> fget;
    public final BiFunction<A, B, A> fset;

    private Lens(Function<A, B> fget, BiFunction<A, B, A> fset) {
        this.fget = fget;
        this.fset = fset;
    }

    public B get(A a) {
        return fget.apply(a);
    }

    public A set(A a, B b) {
        return fset.apply(a, b);
    }

    @Override
    public B apply(A a) {
        return get(a);
    }

    public A updated(A whole, B part) {
        return set(whole, part);
    }

    public A mod(A a, Function<B, B> f) {
        return set(a, f.apply(apply(a)));
    }

    public <C> Lens<C, B> compose(final Lens<C, A> that) {
        return new Lens<>(c -> get(that.apply(c)), (c, b) -> that.mod(c, a -> set(a, b)));
    }

    public <C> Lens<A, C> andThen(Lens<B, C> that) {
        return that.compose(this);
    }

    public static <A,B> Lens<A,B> of(Function<A, B> fget, BiFunction<A, B, A> fset) {
        return new Lens<>(fget, fset);
    }
}
