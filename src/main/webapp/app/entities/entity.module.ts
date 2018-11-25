import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { StBarnabeCEBModule } from './ceb/ceb.module';
import { StBarnabeCarteModule } from './carte/carte.module';
import { StBarnabeFideleModule } from './fidele/fidele.module';
import { StBarnabeVersementModule } from './versement/versement.module';
import { StBarnabeEmployeModule } from './employe/employe.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        StBarnabeCEBModule,
        StBarnabeCarteModule,
        StBarnabeFideleModule,
        StBarnabeVersementModule,
        StBarnabeEmployeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StBarnabeEntityModule {}
