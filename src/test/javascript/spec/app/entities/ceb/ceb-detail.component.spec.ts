/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { CEBDetailComponent } from 'app/entities/ceb/ceb-detail.component';
import { CEB } from 'app/shared/model/ceb.model';

describe('Component Tests', () => {
    describe('CEB Management Detail Component', () => {
        let comp: CEBDetailComponent;
        let fixture: ComponentFixture<CEBDetailComponent>;
        const route = ({ data: of({ cEB: new CEB(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CEBDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CEBDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CEBDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cEB).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
