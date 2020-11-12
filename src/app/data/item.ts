export class Item {
  constructor(
    public title: string,
    public url: string,
    public by: string,
    public score: number,
    public time: number,
    public kids: number[],
    public descendants: number,
    public text: string
  ) {}
}
