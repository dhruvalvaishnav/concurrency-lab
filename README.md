# 🔄 Concurrency Lab - Java Multithreading & Concurrency Patterns

> Practical examples of Java concurrency patterns for building scalable, high-performance backend systems. Covers everything from basic threads to advanced async patterns used in production microservices.

[![Java](https://img.shields.io/badge/Java-11%2B-ED8B00?style=flat-square&logo=java&logoColor=white)](https://www.java.com)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org)
[![Concurrency](https://img.shields.io/badge/Topic-Concurrency-blue?style=flat-square)]()
[![Multithreading](https://img.shields.io/badge/Topic-Multithreading-blue?style=flat-square)]()

---

## 🧪 What's Inside

| Topic | Description |
|-------|-------------|
| **Thread Basics** | Creating and managing threads, lifecycle, daemon threads |
| **Synchronization** | `synchronized`, locks, race condition prevention |
| **ExecutorService** | Thread pools, `FixedThreadPool`, `CachedThreadPool`, `ScheduledExecutorService` |
| **CompletableFuture** | Async pipelines, `thenApply`, `thenCompose`, `allOf`, exception handling |
| **Concurrent Collections** | `ConcurrentHashMap`, `BlockingQueue`, `CopyOnWriteArrayList` |
| **Atomic Variables** | `AtomicInteger`, `AtomicReference`, lock-free programming |

---

## 🚀 Getting Started

```bash
git clone https://github.com/dhruvalvaishnav/concurrency-lab.git
cd concurrency-lab
mvn compile
```

Run individual examples from your IDE or via Maven:
```bash
mvn exec:java -Dexec.mainClass="com.dhruval.concurrency.ExecutorServiceDemo"
```

---

## 🧠 Why This Matters

In production systems like high-throughput Kafka consumers or concurrent REST APIs, understanding Java concurrency is critical. These patterns are drawn from real-world scenarios encountered while building microservices at **Adobe** and **EPAM**.

Common production use cases covered:
- Processing Kafka messages with parallel thread pools
- Async HTTP calls with `CompletableFuture`
- Thread-safe caching without locks
- Preventing deadlocks in multi-resource workflows

---

## 📚 Prerequisites

Basic Java knowledge is assumed. Familiarity with Spring Boot is helpful but not required.

---

## 👨‍💻 Author

**Dhruval Vaishnav** - Senior Software Engineer @ Adobe Inc. | Kafka & Microservices specialist

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=flat-square&logo=linkedin)](https://linkedin.com/in/dhruvalvaishnav)
[![Medium](https://img.shields.io/badge/Medium-Follow-12100E?style=flat-square&logo=medium)](https://medium.com/@vdhruval)

⭐ If this repo helped you ace that Java interview, give it a star!
