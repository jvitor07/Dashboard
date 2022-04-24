import { User } from "../models/user"

export interface AuthResponseDTO {
    token: string,
    user: User
}