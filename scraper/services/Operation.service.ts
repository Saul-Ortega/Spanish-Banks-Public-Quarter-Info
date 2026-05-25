import { Operation } from "../types/Operation.types";

export async function fetchOperationByType(declarationId: number, type: string): Promise<Operation | null> {
    return await fetch(`http://localhost:8080/api/declarations/${declarationId}/operations?type=${type}`)
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}

export async function saveOperation(operation: Operation): Promise<Operation | null> {
    return await fetch(`http://localhost:8080/api/declarations/${operation.declarationId}/operations`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(operation)
        })
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}