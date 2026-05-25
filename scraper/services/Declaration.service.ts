import { Declaration } from "../types/Declaration.types"

export async function fetchDeclarationByQuarter(bankId: number, quarter: string): Promise<Declaration | null> {
    return await fetch(`http://localhost:8080/api/banks/${bankId}/declarations?quarter=${quarter}`)
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}

export async function saveDeclaration(declaration: Declaration): Promise<Declaration | null> {
    return await fetch(`http://localhost:8080/api/banks/${declaration.bankId}/declarations`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(declaration)
        })
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}