export interface Order {
    id: number; // Id is overwritten 
    screeningId: number;
    accountId: number;
    tickets: number;
    popcorn: number[]; // small, medium, large
    soda: number[]; // small, medium, large
}
