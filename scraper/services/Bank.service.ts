import { Bank } from "../types/Bank.types";

export async function fetchBankByDenomination(denomination: string): Promise<Bank | null> {
    return await fetch(`http://localhost:8080/api/banks?denomination=${denomination}`)
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}

export async function saveBank(bank: Bank): Promise<Bank | null> {
    return await fetch('http://localhost:8080/api/banks', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(bank)
        })
        .then((response) => response.ok ? response.json() : null)
        .catch(error => {
            console.error(error);
            return null;
        });
}
