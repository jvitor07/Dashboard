import { User } from "../models/user"

export interface AuthResponseDTO {
    token: string,
    userDetails: {
        user: User
    }
}