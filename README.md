# Edge-Detection

## Prerequisites

Java installation.
## Description

Edge Detection uses Java to run a simple Sobel operator kernel over the pixels of the image after graying the image. It look for edges by checking for pixels with highest value contrast.

## Examples
<p><b> Input Image </b></p>
<img src="/images/Mountains.jpg" width="800" height="500"/>
<p><b> Edge Image </b></p>
<img src="/images/Mountainsconverted.png" width="800" height="500"/>
## Input

### Usage
This application is completely command line based.

- Go into the **src** directory

- ? java Entry [space separated commands]


### Commands available

- Blur: Runs a simple Gaussian blurring over the image.

- Grey: Runs an adjusted graying (mixing RGB values) function over the pixels.

- Edge: First Greys (if not previously specified) then runs the Sobel operator over the pixels. 


### Output
With the input file *[filename].ext*, outputs a file with the name *[filename]_adj.png*.


