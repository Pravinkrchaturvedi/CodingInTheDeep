package ch3_stacks_queues;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

public class ch3_7_AnimalQueue {

   /**
    * An animal shelter holds only dogs and cats, and operates on a strictly
    * "first in, first out" basis. People must adopt either the "oldest" of all animals
    * of the shelter, or they can select whether they would prefer a dog or a cat (and will
    * receive the oldest animal of that type). They cannot select which specific animal they
    * would like. Create data structure to maintain this system and implement operations such
    * as enqueue, dequeueAny, dequeueDog, dequeueCat. You may use the built-in LinkedList
    * data structure
    */
   // Maintain two queues here, one for dog and one for cat
   // Maintain a global variable to assign order to both dogs and cats. The order decides which one
   // to dequeue in dequeueAny()

   public enum AnimalType {
      DOG, CAT;
   }

   public class Animal {
      public int order;
      public AnimalType type;

      public Animal(AnimalType type) {
         this.type = type;
      }

      public String toString() {
         return type + "";
      }
   }

   public class AnimalQueue {
      private Queue<Animal> dog_qu;
      private Queue<Animal> cat_qu;
      private int order;

      public AnimalQueue() {
         order = 0;
         dog_qu = new LinkedList<Animal>();
         cat_qu = new LinkedList<Animal>();
      }

      public void enque(Animal an) {
         order++;
         an.order = order;
         if (an.type == AnimalType.DOG)
            dog_qu.add(an);
         else
            cat_qu.add(an);
      }

      // important here to check the empty of queue
      public Animal dequeAny() {
         if (dog_qu.isEmpty() || cat_qu.isEmpty()) {
            if (dog_qu.isEmpty() && cat_qu.isEmpty())
               throw new NoSuchElementException();
            return dog_qu.isEmpty() ? cat_qu.poll() : dog_qu.poll();
         }
         return dog_qu.peek().order < cat_qu.peek().order ? dog_qu.poll() : cat_qu.poll();
      }

      public Animal dequeDog() {
         return dog_qu.poll();
      }

      public Animal dequeCat() {
         return cat_qu.poll();
      }

      public boolean isEmpty() {
         return dog_qu.isEmpty() && cat_qu.isEmpty();
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         Queue<Animal> tmp_dog = new LinkedList<Animal>(dog_qu);
         Queue<Animal> tmp_cat = new LinkedList<Animal>(cat_qu);
         sb.append("[");
         while (!this.isEmpty()) {
            Animal tmp_an = this.dequeAny();
            sb.append(tmp_an + ", ");
         }
         sb.append("]");
         dog_qu = new LinkedList<Animal>(tmp_dog);
         cat_qu = new LinkedList<Animal>(tmp_cat);
         return sb.toString();
      }
   }

   @Test
   public void test() {
      AnimalQueue aqu = new AnimalQueue();
      Random rd = new Random();
      rd.setSeed(System.currentTimeMillis());
      for (int i = 0; i < 20; i++) {
         int value = rd.nextInt(10);
         Animal an = null;
         if (value < 5)
            an = new Animal(AnimalType.DOG);
         else
            an = new Animal(AnimalType.CAT);
         aqu.enque(an);
         System.out.println("Enque: " + an);
         System.out.println(aqu);
         if (i % 5 == 2) {
            Animal tmp = aqu.dequeAny();
            System.out.println("DequeAny: " + tmp);
            System.out.println(aqu);
         }
         if (i % 5 == 3) {
            Animal tmp = aqu.dequeCat();
            System.out.println("DequeCat: " + tmp);
            System.out.println(aqu);
         }
         if (i % 5 == 4) {
            Animal tmp = aqu.dequeDog();
            System.out.println("DequeDog: " + tmp);
            System.out.println(aqu);
         }
      }
      for (int i = 0; i < 3; i++) {
         Animal tmp = aqu.dequeDog();
         System.out.println("DequeDog: " + tmp);
         System.out.println(aqu);
      }
   }
}
