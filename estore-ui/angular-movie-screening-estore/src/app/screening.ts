/* Screening interface to store the values that are in the JSON objects */
export interface Screening {
  id: number,
  movieId: number,
  ticketsRemaining: number,
  date: string,
  time: string
}
