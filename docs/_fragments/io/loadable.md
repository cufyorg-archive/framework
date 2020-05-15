---
index: 0
layout: fragment
parent: io
title: Loadable
links:
    GitHub: https://github.com/cufyorg/framework/tree/master/src/main/java/cufy/io/loadable
    Javadoc: https://framework.cufy.org/javadoc/cufy/io/loadable/package-summary.html
description: >-
    The ways to load and save data is different from a position to
    another, but at the end it still data and still be loaded and
    saved using protocols and standards. Loadables in the other hand
    don't care about how the data will be transferred, as long as the
    data will be transferred successfully.
---

- `getInputStream(...)` `getReader(...)` returns a buffered
input-stream/reader to get data from the data-source of the loadable.
<br>

- `getOutputStream(...)` `getWriter(...)` returns an
output-stream/writer to save data on the data-source of the loadable. 
<br>

- `load(...)` makes the loadable load data from its data-source to
itself.
<br>

- `save(...)` makes the loadable save data from itself to its
data-source.