import os
import time
import requests
from datetime import datetime

API_KEY = os.getenv("GROQ_API_KEY")
MODEL = "openai/gpt-oss-20b"

# -------------------------------
# 🧠 CONCEPT-BASED STRUCTURE (REPLACES TOPICS)
# -------------------------------
PHASES = {
    "phase1": [
        ("CustomHashMap", "HashMap with collision handling"),
        ("LRUCache", "LRU cache using DLL + O(1) ops"),
        ("ImmutableClassDesign", "Immutable object design in Java"),
        ("ComparatorVsComparable", "Sorting strategies in Java")
    ],
    "phase2": [
        ("ThreadSafeSingleton", "Double-checked locking singleton"),
        ("ThreadPoolCustom", "Custom thread pool executor"),
        ("ProducerConsumerBlockingQueue", "Producer-consumer pattern"),
        ("DeadlockPrevention", "Deadlock detection and prevention")
    ],
    "phase3": [
        ("CompletableFuturePipeline", "Async pipelines using CompletableFuture"),
        ("VirtualThreadsDemo", "Java virtual threads usage"),
        ("RateLimiterTokenBucket", "Token bucket rate limiter"),
        ("CircuitBreakerPattern", "Resilience pattern"),
        ("EventDrivenArchitecture", "Event-driven pub-sub system"),
        ("CacheAsidePattern", "Cache-aside caching strategy"),
        ("IdempotencyHandler", "Idempotent request handling")
    ]
}

# -------------------------------
# ⚙️ CONFIG (GROQ FREE SAFE)
# -------------------------------
BATCH_SIZE = 2
DELAY_BETWEEN_CALLS = 6
DELAY_BETWEEN_BATCHES = 20

# -------------------------------
# ✍️ LIGHTWEIGHT PROMPT (IMPORTANT)
# -------------------------------
def build_prompt(name, desc):
    return f"""
Java skeleton for {name}.
Concept: {desc}

Only class + methods.
No comments.
No explanation.
"""


# -------------------------------
# 🌐 API CALL (RATE SAFE)
# -------------------------------
def call_api(prompt):
    url = "https://api.groq.com/openai/v1/chat/completions"

    for attempt in range(6):
        try:
            res = requests.post(
                url,
                headers={
                    "Authorization": f"Bearer {API_KEY}",
                    "Content-Type": "application/json"
                },
                json={
                    "model": MODEL,
                    "messages": [{"role": "user", "content": prompt}],
                    "max_tokens": 450,   # 🔥 critical for free tier
                    "temperature": 0.2
                }
            )

            if res.status_code == 200:
                return res.json()["choices"][0]["message"]["content"]

            if res.status_code == 429:
                wait = 5 + attempt * 3
                print(f"Rate limit → waiting {wait}s")
                time.sleep(wait)
                continue

            print("API error:", res.text)
            return None

        except Exception as e:
            wait = 5 + attempt * 3
            print("Error:", e)
            time.sleep(wait)

    return None


# -------------------------------
# 🚀 MAIN PIPELINE
# -------------------------------
def main():
    date = datetime.now().strftime("%Y%m%d")

    for phase, concepts in PHASES.items():

        print(f"\n📘 Phase: {phase}")

        for i in range(0, len(concepts), BATCH_SIZE):
            batch = concepts[i:i + BATCH_SIZE]

            print("Batch:", [b[0] for b in batch])

            for name, desc in batch:

                prompt = build_prompt(name, desc)
                result = call_api(prompt)

                if result:
                    filename = f"{name}_{date}.java"
                    with open(filename, "w") as f:
                        f.write(result)
                    print("Generated:", filename)
                else:
                    print("Failed:", name)

                time.sleep(DELAY_BETWEEN_CALLS)

            print("Cooling batch...\n")
            time.sleep(DELAY_BETWEEN_BATCHES)


if __name__ == "__main__":
    main()