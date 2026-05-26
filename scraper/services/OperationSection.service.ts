import { OperationSection } from "../types/OperationSection.types";

export async function fetchOperationSectionByType(operationId: number, type: string): Promise<OperationSection | null> {
    return await fetch(`http://localhost:8080/api/operations/${operationId}/operation-sections?type=${type}`)
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}

export async function saveOperationSection(operationSection: OperationSection): Promise<OperationSection | null> {
    return await fetch(`http://localhost:8080/api/operations/${operationSection.operationId}/operation-sections?type=${operationSection.type}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(operationSection)
        })
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}