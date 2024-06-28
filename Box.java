class Box {
    int width;
    int height;
    int length;
    
    public Box(int width, int height,int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public int getwidth() {
        return width;
    }

    public int getheight() {
        return height;
    }

    public int getlength() {
        return length;
    }

    public int[] getDimensions() {
        return new int[] {this.width, this.length, this.height};
    }

    @Override
    public String toString() {
        return width + " " + height + " " + length;
    }
}