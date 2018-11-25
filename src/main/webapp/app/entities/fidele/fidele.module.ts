import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StBarnabeSharedModule } from 'app/shared';
import {
    FideleComponent,
    FideleDetailComponent,
    FideleDetailVersementsComponent,
    FideleUpdateComponent,
    FideleDeletePopupComponent,
    FideleDeleteDialogComponent,
    fideleRoute,
    fidelePopupRoute
} from './';

const ENTITY_STATES = [...fideleRoute, ...fidelePopupRoute];

@NgModule({
    imports: [StBarnabeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FideleComponent,
        FideleDetailComponent,
        FideleDetailVersementsComponent,
        FideleUpdateComponent,
        FideleDeleteDialogComponent,
        FideleDeletePopupComponent
    ],
    entryComponents: [FideleComponent, FideleUpdateComponent, FideleDeleteDialogComponent, FideleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeFideleModule {}
