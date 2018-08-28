import axios from "axios";

const host = "localhost:8080";

export enum ActionType {
    START_FILLING_DATABASE,
    STOP_FILLING_DATABASE,
    GET_MEASUREMENTS
}

export function startFillingDatabase(): any {
    return {
        payload: axios.get('$(host)/fill'),
        type: ActionType.START_FILLING_DATABASE
    }
}

export function stopFillingDatabase(): any {
    return {
        payload: axios.get('$(host)/stopfill'),
        type: ActionType.STOP_FILLING_DATABASE
    }
}

export function getMeasurements(measurementType: string): any{
    const eventSource: EventSource = new EventSource('${host}/measurements/${measurementType}');
    return {
        payload: eventSource,
        type: ActionType.GET_MEASUREMENTS
    }
}
