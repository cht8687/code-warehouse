/*
   Modified version of FNV hash function
*/
export default class HashGen {
  constructor() {
    this.hash = 0x811C9DC5 /* offset_basis */
  }
  update(data) {
    if (typeof data === 'string') {
      data = Buffer(data)
    } else if(!(data instanceof Buffer)) {
      throw Error("Expectes String or Buffer")
    }

    for(let i = 0; i < data.length; i++) {
      this.hash = this.hash ^ data[i]
      /* 32 bit FNV_Prime = 2**24 + 2**8 + 0x93 */
      this.hash += (this.hash << 24) + (this.hash << 8) + (this.hash << 7) + (this.hash << 4) + (this.hash << 1)
    }
    return this;
  }

  digest(encoding="binary") {
    const buf = new Buffer(4);
    buf.writeInt32BE(this.hash & 0xffffffff, 0)
    return buf.toString(encoding)
  }

  value() {
    return this.hash & 0xffffffff
  }
}
