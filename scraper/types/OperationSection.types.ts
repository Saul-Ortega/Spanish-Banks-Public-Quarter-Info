export interface OperationSection {
    id?: number;
    operationId?: number;
    type: string;
    practiced?: boolean;
    data?: Record<string, any>;
}