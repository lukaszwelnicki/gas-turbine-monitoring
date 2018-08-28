import { ActionType } from "../actions/types";

export function stopDatabaseFill(state: boolean = true, action: any): any {
    switch (action.type) {
        case ActionType.STOP_FILLING_DATABASE:
            return action.payload.data;
        default:
            return state;
    }
}