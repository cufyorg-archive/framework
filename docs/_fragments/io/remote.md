---
index: 1
layout: fragment
parent: io
title: Remote
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/io
    Javadoc: /javadoc/cufy/io/package-summary.html
description: >-
    One of the solutions to control an input-stream, output-stream,
    reader, or a writer concurrently is to wrap it with a control
    instance. The control-instance checks for the state of its
    instructor before doing anything.
---

- `RemoteInputStream` `RemoteReader` `RemoteOutputStream`
`RemoteWriter` runs its read()/write() methods on a new Do-loop and
starts it using its instructor.