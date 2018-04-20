/*
  BitBuffer container used to store buffer
*/
export default class BitBuffer {
  constructor(number) {
    const size = Math.ceil(number / 8);
    this.buffer = new Buffer(size);
    this.buffer.fill(0);
  }

  set(index, bool) {
    const pos = index >>> 3;
    if (bool) {
      this.buffer[pos] |= 1 << (index % 8);
    } else {
      this.buffer[pos] &= ~(1 << (index % 8));
    }
  }

  get(index) {
    return (this.buffer[index >>> 3] & (1 << (index % 8))) !== 0;
  }

  toBuffer() {
    return this.buffer;
  }
}
