import { User } from "../models/user"

export interface AuthResponseDTO {
    token: string,
    useDetails: {
        user: User
    }
}