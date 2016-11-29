package com.google.guava.collect;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class SetsTest {

	@Test
	public void testIntersection(){
		Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
		Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

		SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
		// I can use intersection as a Set directly, but copying it can be more efficient if I use it a lot.
		ImmutableSet<String> immutableCopy = intersection.immutableCopy();
		
		System.out.println(immutableCopy);
	}
	
	@Test
	public void testOtherUtility(){
		Set<String> animals = ImmutableSet.of("gerbil", "hamster");
		Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");

		//笛卡尔乘积
		Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
		// {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
		//  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
		System.out.println(product);
		
		//
		Set<Set<String>> animalSets = Sets.powerSet(animals);
		// {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}
		System.out.println(animalSets);
		System.out.println("-----------");
		for(Set<String> animal : animalSets){
			System.out.println(animal);
		}
		System.out.println("-----------");

	}
}
