package com.xxg.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static AtomicReference<User> atomicReference = new AtomicReference<User>();

    public static void main(String[] args) {
      User user = new User("小埋",4);
      atomicReference.set(user);
      User updateUser = new User("小皮龙",5);
      atomicReference.compareAndSet(user,updateUser);
        System.out.println(atomicReference.get().getUsername());
        System.out.println(atomicReference.get().getAge());
    }

   static class User{
        private String username;
        public  int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
