---
name: explore-codebase
description: Explore an unfamiliar codebase by spawning subagents for structured analysis — project overview, feature deep-dives, and architecture mapping.
---

# Explore Codebase

Systematically explore a codebase you don't yet understand. Uses subagents for parallel investigation, producing a structured summary you can act on.

## When to use

- First encounter with a project or unfamiliar part of a project
- Need to understand architecture before making changes
- User asks "how does X work?" about a codebase feature
- Onboarding to a new repository

## Procedure

### Step 1: Project Overview (subagent)

Spawn an explore subagent with this prompt structure:

```
Give me a high-level overview of this project's structure. I need to understand:

1. The technology stack (language, framework, build system)
2. Top-level directory layout and what each directory contains
3. Entry points (main files, config files, build scripts)
4. Key configuration patterns (env vars, config files, feature flags)
5. How to build, test, and run the project

Project root: <path>
```

### Step 2: Feature Deep-Dive (subagent, repeatable)

For each feature or subsystem you need to understand, spawn an explore subagent:

```
Search very thoroughly for all code related to "<feature>" or "<keyword>" in this project. I need to understand:

1. Where is this feature implemented? (files, functions, classes)
2. What is the data flow? (input → processing → output)
3. What APIs/endpoints does it expose or consume?
4. What database tables or data structures does it use?
5. How does it connect to other parts of the system?
6. Are there tests for this feature? Where?

Project root: <path>
```

### Step 3: Architecture Map

After subagents return, synthesize findings into a concise architecture summary:

- **Stack**: language, framework, runtime
- **Layout**: key directories and their roles
- **Data flow**: how data moves through the system
- **Key files**: the 5-10 files most important for understanding the project
- **Build/test**: commands to build and test
- **Gotchas**: non-obvious patterns, workarounds, or conventions

## Tips

- Start broad (Step 1), then narrow (Step 2). Don't deep-dive before understanding the lay of the land.
- Each subagent call should focus on one concern. Multiple focused calls beat one vague call.
- If the codebase is large, ask subagents to focus on specific directories rather than the whole tree.
- Cross-reference subagent findings against the project's README, CHANGELOG, or docs if they exist.
- Record durable findings in the project's MEMORY.md under "Discovered durable knowledge".

## Stopping condition

You have enough understanding when you can answer: "What are the key files I'd need to modify to change X?" If you can't answer that, you need another deep-dive.
