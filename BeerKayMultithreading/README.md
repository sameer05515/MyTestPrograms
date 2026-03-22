# BeerKayMultithreading

Self-contained **Java multithreading** exercises: small `main`-driven demos grouped by topic (thread basics, synchronization, locks, pools, latches, producer–consumer, deadlocks, semaphores, `Callable`/`Future`, interrupts). Much of the code is adapted from **Cave of Programming**’s Java multithreading material (see class Javadoc links to the YouTube course and Udemy).

**Maven coordinates:** `com.p.multithreading:BeerKayMultithreading:0.0.1-SNAPSHOT`  
**Language level:** Java **8** (`pom.xml` sets `maven.compiler.source` / `target`).

## Requirements

- **JDK 8 or newer**
- **Maven 3.x** (optional; Eclipse with M2E also works)

## Build

```bash
cd BeerKayMultithreading
mvn compile
```

Compiled classes go to `target/classes`.

## Run a demo

Pick a class that defines `public static void main` and run it with the module on the classpath. Examples:

```bash
# After mvn compile, from project root (BeerKayMultithreading)
java -cp target/classes StartingThreads_1.ApplicationRunnable
java -cp target/classes VolatileKeyword_2.App
java -cp target/classes Deadlock_11.App
```

The default-package scratch class:

```bash
java -cp target/classes tests
```

In **Eclipse**, use *Run As → Java Application* on the desired class.

## Topics by package

| Package | Focus |
| --- | --- |
| `StartingThreads_1` | Extends `Thread`, `Runnable`, anonymous thread |
| `VolatileKeyword_2` | `volatile` visibility |
| `JoiningAndSynchronizeThreads_3` | `join()`, synchronized workers |
| `LockObjects_4` | Lock objects, synchronized methods |
| `ThreadPools_5` | Executor / thread pool (`App`, `WorkerThreadPool`) |
| `CountDownLatch_6` | `CountDownLatch` coordination |
| `ProducerConsumer_7` | Producer–consumer pattern |
| `WaitAndNotify_8` | `wait` / `notify`, `BlockingQueue` variant |
| `LowLevelProducerConsumer_9` | Lower-level queue handoff |
| `ReentrantLocks_10` | `ReentrantLock` |
| `Deadlock_11` | Deadlock demo (`App`, `Runner`, `SimpleDeadLock`, `Account`) |
| `Semaphores_12` | `Semaphore`, connection-style example |
| `CallableAndFuture_13` | `Callable`, `Future`, executors (`App`, `App2`, `CallableTester`) |
| `InterruptingThreads14` | Thread interruption |

## Project layout

- `src/main/java/<Package>/` — one topic per package; entry points are usually `App.java` or similarly named classes (see `main` methods under `JoiningAndSynchronizeThreads_3/Worker.java`, etc.).
- `pom.xml` — minimal JAR project; no third-party dependencies (standard library only).
- `.classpath`, `.project`, `.settings/` — Eclipse metadata (often committed for this repo).

## Credits

Several files cite **Cave of Programming** and the **Java Multithreading** Udemy course in Javadoc. Author annotations in source: **Z.B. Celik** (`celik.berkay@gmail.com`).

## Notes

- This is a **learning** workspace, not a single runnable application: run individual classes as needed.
- Demos that spin forever or deadlock are intentional for teaching; stop the JVM from your IDE or with Ctrl+C in a terminal.
