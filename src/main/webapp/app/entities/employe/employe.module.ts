import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StBarnabeSharedModule } from 'app/shared';
import { StBarnabeAdminModule } from 'app/admin/admin.module';
import {
    EmployeComponent,
    EmployeDetailComponent,
    EmployeUpdateComponent,
    EmployeDeletePopupComponent,
    EmployeDeleteDialogComponent,
    employeRoute,
    employePopupRoute
} from './';

const ENTITY_STATES = [...employeRoute, ...employePopupRoute];

@NgModule({
    imports: [StBarnabeSharedModule, StBarnabeAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeComponent,
        EmployeDetailComponent,
        EmployeUpdateComponent,
        EmployeDeleteDialogComponent,
        EmployeDeletePopupComponent
    ],
    entryComponents: [EmployeComponent, EmployeUpdateComponent, EmployeDeleteDialogComponent, EmployeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeEmployeModule {}
