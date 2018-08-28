import { ActionType } from "../actions/types";

export function startDatabaseFill(state: boolean = false, action: any): any {
    switch (action.type) {
        case ActionType.START_FILLING_DATABASE:
            return action.payload.data;
        default:
            return state;
    }
}