# NFJE

A long time ago (~2001) , I wrote a little 3D rendering engine in java. The original code is still available on
[sourceforge](https://sourceforge.net/projects/nfje/) .

It supports 3d rendering of polygons, texture mapping and shading.

In essence, it renders polygons into a Z-buffer via a scanline conversion algorithm that also tracks
the Z-coordinate of the spans, as well as the coordinates on the texture.

Recently (2019) I got interested how fast it would be on a modern CPU, so I tried to get it compiled again...
It (almost) worked out of the box. I cleaned it up a bit (I needed to slow down the object movement in the demos,
increase the window size, removed deprecations, introduced generic, ...), and here we are (it's pretty fast).

## Note:
There was an opengl renderer as well, but I couldn't find the jar it depended on anymore. Ah well, whatever

## Screenshot
![screenshot](/images/mushroom_and_cubes.png)
