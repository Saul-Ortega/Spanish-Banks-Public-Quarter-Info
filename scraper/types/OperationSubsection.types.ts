export interface OperationSubsection {
    id?: number;
    operationSectionId?: number;
    type: string;
    practiced: boolean;
    data: Record<string, any>;
}