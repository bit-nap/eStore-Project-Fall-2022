/* Sodas interface to store the values that are in the JSON objects */
import { Size } from "./Size";

export interface Sodas {
  id: number,
  name: string,
  poster: string,
  sizes: Size[]
}
  