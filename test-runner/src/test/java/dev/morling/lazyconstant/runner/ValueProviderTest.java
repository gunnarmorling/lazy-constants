package dev.morling.lazyconstant.runner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dev.morling.lazyconstant.LazyConstantDemo;

public class ValueProviderTest {

//    @Test
//    public void testConstantDynamicAsm() {
//        ValueProviderConstantDynamic vp = new ValueProviderConstantDynamic();
//        assertEquals("huzza", vp.getValue());
//    }
//
//    @Test
//    public void testConstantDynamicByteBuddy() {
//        ValueProviderConstantDynamicBb vp = new ValueProviderConstantDynamicBb();
//        assertEquals("huzza", vp.getValue());
//    }

//    @Test
//    public void testConstantDynamicByteBuddyMemberSubstitution() {
//        ValueProviderLazyConstant vp = new ValueProviderLazyConstant();
//        assertEquals("huzza", vp.getValue());
//        assertEquals("huzza", vp.getValue());
//    }

    @Test
    public void testLazyConstant() {
        LazyConstantDemo vp = new LazyConstantDemo();
        assertEquals("eager", vp.getEagerValue());
        assertEquals("lazy", vp.getLazyValue());
        assertEquals("lazy", vp.getLazyValue());
    }
}
