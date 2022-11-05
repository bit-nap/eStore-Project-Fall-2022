/* Interface for an Order data object */
export interface Order {
  id: number,
  screeningId: number,
  accountId: number,
  tickets: number,
  popcorn: number[],
  soda: number[],
  seats: string[]
}
